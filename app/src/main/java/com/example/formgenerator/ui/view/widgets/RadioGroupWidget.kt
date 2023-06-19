package com.example.formgenerator.ui.view.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.formgenerator.ui.theme.FormGeneratorTheme
import com.example.formgenerator.ui.view.ValidationCheckModel

@Composable
fun RadioGroupWidget(
    items: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
    validationCheckModel: ValidationCheckModel,
    text: String
) {

    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text(text = text)
        Row {
            items.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = it == value,
                        onClick = { onValueChange(it) }
                    )
                    Text(text = it)
                }
            }
        }
        AnimatedVisibility(visible = !validationCheckModel.valid) {
            Text(text = validationCheckModel.message ?: "", color = Color.Red)
        }
    }
}

@Preview
@Composable
fun RadioGroupWidgetPreview() {
    FormGeneratorTheme {
        Surface {
            RadioGroupWidget(
                items = listOf(
                    "Option 1",
                    "Option 2"
                ),
                onValueChange = {},
                validationCheckModel = ValidationCheckModel("", false),
                text = "question",
                value = ""
            )
        }
    }
}