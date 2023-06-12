package com.example.formgenerator.ui.view.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.formgenerator.ui.theme.FormGeneratorTheme
import com.example.formgenerator.ui.view.ValidationCheckModel

@Composable
fun TextFieldWidget(
    onValueChange: (String) -> Unit,
    value: String,
    validationCheckModel: ValidationCheckModel,
    hint: String?
) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(bottom = 8.dp),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = {
                Text(text = hint ?: "")
            },
            isError = !validationCheckModel.valid,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent, unfocusedIndicatorColor = MaterialTheme.colors.secondary)
        )
        AnimatedVisibility(visible = !validationCheckModel.valid) {
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