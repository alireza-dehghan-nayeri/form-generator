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

    // the form config that was received from server
    val form = viewModel.form

    // the form value that was received from server can be empty if it is the first time
    val formValueState = remember {
        viewModel.formValue
    }

    val screenConfigs = form?.formConfig?.screenConfigs ?: listOf()

    // keep track of the screen being seen by user
    var currentScreen by remember {
        mutableStateOf(0)
    }

    // as there might be a dependency between screens which may lead to a screen being invisible we keep track of the last screen
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

    // the lambda which is being used to update the form value
    val onValueChange: (Map<String, Any?>) -> Unit = {
        formValueState[it.keys.first().toString()] = it[it.keys.first()]
    }

    Scaffold {

        screenConfigs.getOrNull(currentScreen)
            ?.let { config ->
                // first we check if the screen dependency condition is true as the screen is either visible or invisible by dependency condition
                if (dependencyHandler(
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
                // if the screen dependency condition is false so we should skip the screen
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

    // we use this list to see if there is any invalid widget
    val validationCheckModels = mutableListOf<ValidationCheckModel>()

    // we use this variable to know when to show the validation errors to user as the errors should not be show if it is the first time user sees the screen
    var shouldShowValidationError by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            Row {
                Button(onClick = {
                    if (validationCheckModels.all() {
                            it.valid
                        }) {
                        nextScreen.invoke()
                    } else {
                        shouldShowValidationError = true
                    }
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
        // we clear the list as the whole screen is being recomposed after value change so the previous validation errors are useless
        validationCheckModels.clear()
        Column(Modifier.padding(paddingValues)) {
            widgetConfigs.forEach { widgetConfig ->
                // we check the dependency condition of the widget
                if (dependencyHandler(
                        dependencies = widgetConfig.dependencies,
                        formValue = formValue
                    )
                ) {
                    // we check the if the widget value is valid
                    val validationCheckModel = widgetValidationHandler(
                        widgetConfig = widgetConfig,
                        formValue = formValue
                    )

                    validationCheckModels.add(validationCheckModel)

                    Widget(
                        widgetConfig = widgetConfig,
                        value = formValue[widgetConfig.dataPath],
                        onValueChange = { value ->
                            shouldShowValidationError = true
                            onValueChange.invoke(value)
                        },
                        validationCheckModel = if (shouldShowValidationError) validationCheckModel else ValidationCheckModel(
                            null,
                            true
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
    // we check the widget type and show the widget accordingly
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
                if (value.isNullOrBlank()) {
                    validationCheckModel.valid = false
                    validationCheckModel.message = it.message
                }
            }
            ValidationType.DEPENDENT -> {
                if (!dependencyHandler(
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
fun dependencyHandler(
    dependencies: List<DependencyConfig>,
    formValue: Map<String, Any?>
): Boolean {
    val booleanList = mutableListOf<Boolean>()
    val jsonLogic = JsonLogic()
    var conditionEvaluation = true
    if (dependencies.isNotEmpty()) {
        dependencies.forEach {
            val rule = JSONObject(it.rule).toString()
            val output = jsonLogic.apply(rule, formValue)
            booleanList.add(
                output.toString() == "true"
            )
        }
        conditionEvaluation = !booleanList.any { !it }
    }
    return conditionEvaluation
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
