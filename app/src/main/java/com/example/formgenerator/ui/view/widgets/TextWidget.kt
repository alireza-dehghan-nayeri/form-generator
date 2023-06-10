package com.example.formgenerator.ui.view.widgets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.formgenerator.ui.theme.FormGeneratorTheme

@Composable
fun TextWidget(text: String?) {
    Text(text = text ?: "")
}

@Preview(showBackground = true)
@Composable
fun TextWidgetPreview() {
    FormGeneratorTheme {
        TextWidget(
            text = "text"
        )
    }
}