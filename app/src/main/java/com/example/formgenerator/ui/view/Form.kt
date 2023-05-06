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
import com.example.formgenerator.model.ScreenConfig
import com.example.formgenerator.model.ValidationConfig
import com.example.formgenerator.model.WidgetConfig
import com.example.formgenerator.model.WidgetType
import io.github.jamsesso.jsonlogic.JsonLogic
import org.json.JSONObject
import javax.xml.validation.ValidatorHandler

data class ValidationCheckModel(
    val message: String?,
    val valid: Boolean
)

@Composable
fun Form(viewModel: ManiViewModel) {

    val form = viewModel.form
    Log.d("TAGTAG", "inflate form ${form?.formConfig?.id}")

    val formValueState = remember {
        mutableStateOf(viewModel.formValue)
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
        formValueState.value = formValueState.value + it
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

        screenConfigs.getOrNull(currentScreen)
            ?.let { config ->
                if (screenDependencyHandler(screenConfig = config, formValue = formValueState.value))
                    Screen(
                        paddingValues = it,
                        screenConfig = config,
                        formValue = formValueState.value,
                        onValueChange = onValueChange
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
fun screenDependencyHandler(screenConfig: ScreenConfig, formValue: Map<String, Any?>): Boolean {
    val booleanList = mutableListOf<Boolean>()
    val jsonLogic = JsonLogic()
    var shouldShowScreen = true
    if (screenConfig.dependencies.isNotEmpty()) {
        screenConfig.dependencies.forEach {
            val rule = JSONObject(it.rule).toString()
            val output = jsonLogic.apply(rule, formValue)
            Log.d(
                "TAGTAG",
                "screen ${screenConfig.id} json logic output : $output with rule: $rule and data: $formValue"
            )
            booleanList.add(
                output.toString() == "true"
            )
        }
        shouldShowScreen = !booleanList.any { !it }
    }
    return shouldShowScreen
}

@Composable
fun Screen(
    paddingValues: PaddingValues,
    screenConfig: ScreenConfig,
    formValue: Map<String, Any?>,
    onValueChange: (Map<String, Any>) -> Unit
) {
    Log.d("TAGTAG", "inflate screen ${screenConfig.id}")

    val widgetConfigs = screenConfig.widgetConfigs

    Column(Modifier.padding(paddingValues)) {
        widgetConfigs.forEach {
            if (widgetDependencyHandler(widgetConfig = it, formValue = formValue)) {
                Widget(widgetConfig = it, formValue = formValue, onValueChange = onValueChange)
            }
        }
    }
}

@Composable
fun widgetDependencyHandler(widgetConfig: WidgetConfig, formValue: Map<String, Any?>): Boolean {
    val booleanList = mutableListOf<Boolean>()
    val jsonLogic = JsonLogic()
    var shouldShowWidget = true
    if (widgetConfig.dependencies.isNotEmpty()) {
        widgetConfig.dependencies.forEach {
            val rule = JSONObject(it.rule).toString()
            val output = jsonLogic.apply(rule, formValue)
            Log.d(
                "TAGTAG",
                "widget ${widgetConfig.id} json logic output : $output with rule: $rule and data: $formValue"
            )
            booleanList.add(
                output.toString() == "true"
            )
        }
        shouldShowWidget = !booleanList.any { !it }
    }
    return shouldShowWidget
}

@Composable
fun Widget(
    widgetConfig: WidgetConfig,
    formValue: Map<String, Any?>,
    onValueChange: (Map<String, Any>) -> Unit
) {
    when (WidgetType.valueOf(widgetConfig.type)) {
        WidgetType.TEXT_FIELD -> TextFieldWidget(
            widgetConfig = widgetConfig,
            value = formValue[widgetConfig.dataPath]?.toString() ?: "".ifBlank { "" },
            onValueChange = {
                onValueChange(mapOf(Pair(widgetConfig.dataPath, it)))
            }
        )
        WidgetType.RADIO_BUTTON -> TODO()
        WidgetType.RADIO_GROUP -> TODO()
        WidgetType.CHECKBOX -> TODO()
        WidgetType.CHECKBOX_GROUP -> TODO()
        WidgetType.DROP_DOWN_MENU -> TODO()
        WidgetType.BOTTOM_SHEET_SINGLE_SELECT -> TODO()
        WidgetType.BOTTOM_SHEET_MULTI_SELECT -> TODO()
        WidgetType.COUNTER -> TODO()
        WidgetType.TEXT -> TextWidget(widgetConfig = widgetConfig)
        WidgetType.DATE_PICKER -> TODO()
        WidgetType.FILE_UPLOADER -> TODO()
    }
}

//@Composable
//fun widgetValidationHandler(validations: List<ValidationConfig>):ValidationCheckModel {
//
//}

@Composable
fun TextFieldWidget(widgetConfig: WidgetConfig, value: String, onValueChange: (String) -> Unit) {

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(text = widgetConfig.hint ?: "")
        }
    )
}

@Composable
fun TextWidget(widgetConfig: WidgetConfig) {
    Text(text = widgetConfig.text ?: "")
}
