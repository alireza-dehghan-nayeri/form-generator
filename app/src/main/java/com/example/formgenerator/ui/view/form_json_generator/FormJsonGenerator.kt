package com.example.formgenerator.ui.view.form_json_generator

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.formgenerator.model.FormConfig
import com.example.formgenerator.model.ScreenConfig
import com.example.formgenerator.model.WidgetConfig
import com.example.formgenerator.ui.view.ManiViewModel

@Composable
fun FormJsonGenerator(viewModel: ManiViewModel) {

    val currentFormId = 0
    var currentScreenId = 0

    val screenConfigs: MutableList<ScreenConfig> = mutableListOf()

    screenConfigs.add(
        ScreenConfig(
            dependencies = listOf(),
            id = currentScreenId,
            widgetConfigs = listOf()
        )
    )
    var formConfig = FormConfig(id = currentFormId, screenConfigs = screenConfigs)

    var currentScreen by remember {
        mutableStateOf(0)
    }

    val nextScreen = {
        if (currentScreen < screenConfigs.size) {
            currentScreen++
        }
        if (currentScreen == screenConfigs.size - 1) {
            currentScreenId += 1
            screenConfigs.add(
                ScreenConfig(
                    dependencies = listOf(),
                    id = currentScreenId,
                    widgetConfigs = listOf()
                )
            )
            formConfig = FormConfig(currentFormId, screenConfigs)
        }
    }

    val previousScreen = {
        if (currentScreen > 0) {
            currentScreen--
        }
    }

    Scaffold(bottomBar = {
        Row {
            Button(onClick = {
                nextScreen.invoke()
            }) {
                Text(text = "next")
            }

            Button(onClick = {
                previousScreen.invoke()
            }) {
                Text(text = "previous")
            }
        }
    })
    {
        it
        screenConfigs.getOrNull(currentScreen)?.let { config ->
            ScreenJsonGenerator(config){
                
            }
        }
        if (currentScreen == screenConfigs.size) {
            Button(onClick = {
                Log.d("TAGTAG", "this is the form value now")
                Log.d("TAGTAG", formConfig.toString())
            }) {
                Text(text = "SAVE")
            }
        }
    }
}

@Composable
fun ScreenJsonGenerator(screenConfig: ScreenConfig, onAddWidget: (WidgetConfig) -> Unit) {
    val widgetConfigs = screenConfig.widgetConfigs
    Column {
        widgetConfigs.forEach {
            WidgetJsonGenerator(widgetConfig = it)
        }
        Button(onClick = {

        }) {
            Text(text = "add widget")
        }
    }
}



@Composable
fun WidgetJsonGenerator(widgetConfig: WidgetConfig) {

}