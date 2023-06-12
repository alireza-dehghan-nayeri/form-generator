package com.example.formgenerator.ui.view.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.formgenerator.ui.theme.FormGeneratorTheme

@Composable
fun TextWidget(text: String?) {

    Text(modifier = Modifier.fillMaxWidth(), text = text ?: "")
    Spacer(modifier = Modifier.height(8.dp))

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