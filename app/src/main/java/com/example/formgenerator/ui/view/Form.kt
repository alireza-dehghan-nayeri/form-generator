package com.example.formgenerator.ui.view

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun Form(viewModel: ManiViewModel) {

    // the form config that was received from server
    val form = viewModel.form

    // the form value that was received from server can be empty if it is the first time
    val formValueState = remember {
        viewModel.formValue
    }

    val screenConfigs = form?.formConfig?.screenConfigs ?: listOf()

    // keep track of the screen being seen by user
    var currentScreen by remember {
        mutableStateOf(0)
    }

    // as there might be a dependency between screens which may lead to a screen being invisible we keep track of the last screen
    var lastScreen by remember {
        mutableStateOf(0)
    }

    val nextScreen = {
        if (currentScreen < screenConfigs.size) {
            lastScreen = currentScreen
            currentScreen++
        }
    }

    val previousScreen = {
        if (currentScreen > 0) {
            lastScreen = currentScreen
            currentScreen--
        }
    }

    // the lambda which is being used to update the form value
    val onValueChange: (Map<String, Any?>) -> Unit = {
        formValueState[it.keys.first().toString()] = it[it.keys.first()]
    }

    Scaffold {
        screenConfigs.getOrNull(currentScreen)
            ?.let { config ->

                // first we check if the screen dependency condition is true as the screen is either visible or invisible by dependency condition
                if (dependencyHandler(
                        dependencies = config.dependencies,
                        formValue = formValueState
                    )
                )
                    Screen(
                        paddingValues = it,
                        screenConfig = config,
                        formValue = formValueState,
                        onValueChange = onValueChange,
                        nextScreen = nextScreen,
                        previousScreen = previousScreen
                    )
                // if the screen dependency condition is false so we should skip the screen
                else {
                    if (lastScreen <= currentScreen) {
                        nextScreen.invoke()
                    }
                    if (lastScreen > currentScreen) {
                        previousScreen.invoke()
                    }
                }
            }

        if (currentScreen == screenConfigs.size) {
            Button(onClick = {
                Log.d("TAGTAG", "this is the form value now")
                Log.d("TAGTAG", formValueState.toString())
            }) {
                Text(text = "SAVE")
            }
        }
    }
}
