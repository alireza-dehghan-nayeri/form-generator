package com.example.formgenerator.ui.view

import androidx.compose.runtime.Composable
import com.example.formgenerator.model.ValidationType
import com.example.formgenerator.model.WidgetConfig


data class ValidationCheckModel(
    var message: String?,
    var valid: Boolean
)

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
        }
    }
    return validationCheckModel
}