package com.example.formgenerator.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.formgenerator.model.ScreenConfig
import com.example.formgenerator.model.WidgetConfig
import com.example.formgenerator.model.WidgetType
import com.example.formgenerator.ui.theme.FormGeneratorTheme
import io.github.jamsesso.jsonlogic.JsonLogic
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ManiViewModel by viewModels()
        setContent {
            FormGeneratorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Form(viewModel = viewModel)
                }
            }
        }
    }
}



