package com.example.formgenerator.ui.view.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.formgenerator.ui.theme.FormGeneratorTheme
import com.example.formgenerator.ui.view.ValidationCheckModel

@Composable
fun RadioGroupWidget(
    items: List<String>,
    onValueChange: (String) -> Unit,
    validationCheckModel: ValidationCheckModel,
    text: String
) {

    // todo : add validation

    var selected by remember {
        mutableStateOf(items[0])
    }

    Column {
        Text(text = text)
        Row {
            items.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    RadioButton(selected = it == selected, onClick = {
                        onValueChange(it)
                        selected = it
                    })
                    Text(text = it)
                }
            }
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
                onValueChange = {
                    it
                },
                validationCheckModel = ValidationCheckModel("", false),
                text = "question"
            )
        }
    }
}