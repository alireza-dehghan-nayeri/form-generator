package com.example.formgenerator.ui.view.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.formgenerator.ui.theme.FormGeneratorTheme
import com.example.formgenerator.ui.view.ValidationCheckModel

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

@Preview(showBackground = true)
@Composable
fun TextFieldWidgetPreview() {
    var value by remember {
        mutableStateOf("")
    }
    FormGeneratorTheme {
        TextFieldWidget(
            onValueChange = {
                value = it
            }, value = value,
            validationCheckModel = ValidationCheckModel(null, true),
            hint = "hint"
        )
    }
}