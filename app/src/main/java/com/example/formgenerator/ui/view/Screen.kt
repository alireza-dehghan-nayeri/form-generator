package com.example.formgenerator.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.formgenerator.model.ScreenConfig

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
        modifier = Modifier.padding(paddingValues),
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
        // we clear the list as the whole screen is being recomposed after value change so the previous validation errors are useless
        validationCheckModels.clear()
        Column(Modifier.padding(it)) {
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