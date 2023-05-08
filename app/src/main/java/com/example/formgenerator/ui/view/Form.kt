package com.example.formgenerator.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.formgenerator.model.*
import io.github.jamsesso.jsonlogic.JsonLogic
import org.json.JSONObject

data class ValidationCheckModel(
    var message: String?,
    var valid: Boolean
)

@Composable
fun Form(viewModel: ManiViewModel) {

    val form = viewModel.form

    val formValueState = remember {
        viewModel.formValue
    }

    val screenConfigs = form?.formConfig?.screenConfigs ?: listOf()

    var currentScreen by remember {
        mutableStateOf(0)
    }

    var lastScreen by remember {
        mutableStateOf(0)
    }

    val nextScreen = {
        if (currentScreen < screenConfigs.size) {
            lastScreen = currentScreen
            currentScreen++
        }
    }

    val previousScreen = {
        if (currentScreen > 0) {
            lastScreen = currentScreen
            currentScreen--
        }
    }

    val onValueChange: (Map<String, Any?>) -> Unit = {
        formValueState[it.keys.first().toString()] = it[it.keys.first()]
    }

    Scaffold {

        screenConfigs.getOrNull(currentScreen)
            ?.let { config ->
                if (screenDependencyHandler(
                        dependencies = config.dependencies,
                        formValue = formValueState
                    )
                )
                    Screen(
                        paddingValues = it,
                        screenConfig = config,
                        formValue = formValueState,
                        onValueChange = onValueChange,
                        nextScreen = nextScreen,
                        previousScreen = previousScreen
                    )
                else {
                    if (lastScreen <= currentScreen) {
                        nextScreen.invoke()
                    }
                    if (lastScreen > currentScreen) {
                        previousScreen.invoke()
                    }
                }
            }

        if (currentScreen == screenConfigs.size) {
            Button(onClick = {
                Log.d("TAGTAG", "this is the form value now")
                Log.d("TAGTAG", formValueState.toString())
            }) {
                Text(text = "SAVE")
            }
        }
    }

}

@Composable
fun Screen(
    paddingValues: PaddingValues,
    screenConfig: ScreenConfig,
    formValue: Map<String, Any?>,
    onValueChange: (Map<String, Any>) -> Unit,
    nextScreen: () -> Unit,
    previousScreen: () -> Unit
) {
    val widgetConfigs = screenConfig.widgetConfigs

    var allWidgetsValidated by remember {
        mutableStateOf(true)
    }

    val onWidgetValidationError = {
        allWidgetsValidated = false
    }

    Scaffold(
        bottomBar = {
            Row {
                Button(onClick = {

                    nextScreen.invoke()
                }) {
                    Text(text = "next")
                }

                Button(onClick = {
                    previousScreen.invoke()
                }) {
                    Text(text = "previous")
                }
            }
        }
    ) {
        it
        Column(Modifier.padding(paddingValues)) {
            widgetConfigs.forEach { widgetConfig ->
                if (widgetDependencyHandler(
                        dependencies = widgetConfig.dependencies,
                        formValue = formValue
                    )
                ) {
                    Widget(
                        widgetConfig = widgetConfig,
                        value = formValue[widgetConfig.dataPath],
                        onValueChange = onValueChange,
                        validationCheckModel = widgetValidationHandler(
                            widgetConfig = widgetConfig,
                            formValue = formValue
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun Widget(
    widgetConfig: WidgetConfig,
    value: Any?,
    onValueChange: (Map<String, Any>) -> Unit,
    validationCheckModel: ValidationCheckModel = ValidationCheckModel(message = null, valid = true)
) {
    when (WidgetType.valueOf(widgetConfig.type)) {
        WidgetType.TEXT_FIELD -> TextFieldWidget(
            onValueChange = {
                onValueChange(mapOf(Pair(widgetConfig.dataPath, it)))
            },
            value = value?.toString() ?: "".ifBlank { "" },
            validationCheckModel = validationCheckModel,
            hint = widgetConfig.hint
        )
        WidgetType.RADIO_BUTTON -> TODO()
        WidgetType.RADIO_GROUP -> TODO()
        WidgetType.CHECKBOX -> TODO()
        WidgetType.CHECKBOX_GROUP -> TODO()
        WidgetType.DROP_DOWN_MENU -> TODO()
        WidgetType.BOTTOM_SHEET_SINGLE_SELECT -> TODO()
        WidgetType.BOTTOM_SHEET_MULTI_SELECT -> TODO()
        WidgetType.COUNTER -> TODO()
        WidgetType.TEXT -> TextWidget(text = widgetConfig.text)
        WidgetType.DATE_PICKER -> TODO()
        WidgetType.FILE_UPLOADER -> TODO()
    }
}

@Composable
fun widgetValidationHandler(
    widgetConfig: WidgetConfig,
    formValue: Map<String, Any?>
): ValidationCheckModel {
    val validations = widgetConfig.validations ?: listOf()
    val validationCheckModel = ValidationCheckModel(message = null, valid = true)
    validations.forEach {
        when (ValidationType.valueOf(it.type)) {
            ValidationType.REQUIRED -> {
                val value = formValue[widgetConfig.dataPath]?.toString()
                if (value?.isBlank() == true) {
                    validationCheckModel.valid = false
                    validationCheckModel.message = it.message
                }
            }
            ValidationType.DEPENDENT -> {
                if (!widgetDependencyHandler(
                        dependencies = it.dependencies,
                        formValue = formValue
                    )
                ) {
                    validationCheckModel.valid = false
                    validationCheckModel.message = it.message
                }
            }
            else -> {}
        }
    }
    return validationCheckModel
}

@Composable
fun screenDependencyHandler(
    dependencies: List<DependencyConfig>,
    formValue: Map<String, Any?>
): Boolean {
    val booleanList = mutableListOf<Boolean>()
    val jsonLogic = JsonLogic()
    var shouldShowScreen = true
    if (dependencies.isNotEmpty()) {
        dependencies.forEach {
            val rule = JSONObject(it.rule).toString()
            val output = jsonLogic.apply(rule, formValue)
            booleanList.add(
                output.toString() == "true"
            )
        }
        shouldShowScreen = !booleanList.any { !it }
    }
    return shouldShowScreen
}

@Composable
fun widgetDependencyHandler(
    dependencies: List<DependencyConfig>,
    formValue: Map<String, Any?>
): Boolean {
    val booleanList = mutableListOf<Boolean>()
    val jsonLogic = JsonLogic()
    var shouldShowWidget = true
    if (dependencies.isNotEmpty()) {
        dependencies.forEach {
            val rule = JSONObject(it.rule).toString()
            val output = jsonLogic.apply(rule, formValue)

            booleanList.add(
                output.toString() == "true"
            )
        }
        shouldShowWidget = !booleanList.any { !it }
    }
    return shouldShowWidget
}

@Composable
fun TextFieldWidget(
    onValueChange: (String) -> Unit,
    value: String,
    validationCheckModel: ValidationCheckModel,
    hint: String?
) {
    Column {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = {
                Text(text = hint ?: "")
            },
            isError = !validationCheckModel.valid
        )
        if (!validationCheckModel.valid) {
            Text(text = validationCheckModel.message ?: "", color = Color.Red)
        }
    }
}

@Composable
fun TextWidget(text: String?) {
    Text(text = text ?: "")
}
