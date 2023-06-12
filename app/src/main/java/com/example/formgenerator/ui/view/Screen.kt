package com.example.formgenerator.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.formgenerator.model.ScreenConfig
import com.example.formgenerator.ui.theme.FormGeneratorTheme

@Composable
fun Screen(
    paddingValues: PaddingValues,
    screenConfig: ScreenConfig,
    formValue: Map<String, Any?>,
    onValueChange: (Map<String, Any>) -> Unit,
    validationCheckModels: MutableList<ValidationCheckModel>,
    shouldShowValidationError: Boolean,
    onShouldShowValidationErrorChange: (Boolean) -> Unit
) {
    val widgetConfigs = screenConfig.widgetConfigs


    // we clear the list as the whole screen is being recomposed after value change so the previous validation errors are useless
    validationCheckModels.clear()
    Column( //TODO : change it to lazyColumn
        Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                        onShouldShowValidationErrorChange(true)
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


@Preview
@Composable
fun PreviewScreen() {
    FormGeneratorTheme {
        Screen(
            paddingValues = PaddingValues(24.dp),
            screenConfig = ScreenConfig(dependencies = listOf(), id = 1, widgetConfigs = listOf()),
            formValue = mapOf(),
            onValueChange = {},
            validationCheckModels = mutableListOf(
                ValidationCheckModel(
                    message = "",
                    valid = false
                )
            ),
            shouldShowValidationError = true,
            onShouldShowValidationErrorChange = {}
        )
    }
}