package com.example.formgenerator.ui.view

import androidx.compose.runtime.Composable
import com.example.formgenerator.model.WidgetConfig
import com.example.formgenerator.model.WidgetType
import com.example.formgenerator.ui.view.widgets.DropDownMenuWidget
import com.example.formgenerator.ui.view.widgets.RadioGroupWidget
import com.example.formgenerator.ui.view.widgets.TextFieldWidget
import com.example.formgenerator.ui.view.widgets.TextWidget

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
            hint = widgetConfig.hint,
            keyboardType = widgetConfig.keyboardType ?: ""
        )

        WidgetType.RADIO_BUTTON -> TODO()
        WidgetType.RADIO_GROUP -> RadioGroupWidget(
            items = widgetConfig.items ?: listOf(),
            value = value?.toString() ?: "".ifBlank { "" },
            onValueChange = {
                onValueChange(mapOf(Pair(widgetConfig.dataPath, it)))
            },
            validationCheckModel = validationCheckModel,
            text = widgetConfig.text ?: ""
        )

        WidgetType.CHECKBOX -> TODO()
        WidgetType.CHECKBOX_GROUP -> TODO()
        WidgetType.DROP_DOWN_MENU -> DropDownMenuWidget(
            options = widgetConfig.options ?: listOf(),
            value = value?.toString() ?: "".ifBlank { "" },
            onValueChange = {
                onValueChange(mapOf(Pair(widgetConfig.dataPath, it)))
            },
            validationCheckModel = validationCheckModel,
            text = widgetConfig.text?:""
        )

        WidgetType.BOTTOM_SHEET_SINGLE_SELECT -> TODO()
        WidgetType.BOTTOM_SHEET_MULTI_SELECT -> TODO()
        WidgetType.COUNTER -> TODO()
        WidgetType.TEXT -> TextWidget(text = widgetConfig.text)
        WidgetType.DATE_PICKER -> TODO()
        WidgetType.FILE_UPLOADER -> TODO()
    }
}
