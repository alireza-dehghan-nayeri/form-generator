package com.example.formgenerator.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.formgenerator.ui.theme.FormGeneratorTheme


@Composable
fun Form(viewModel: MainViewModel) {

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

    // we use this list to see if there is any invalid widget
    val validationCheckModels = mutableListOf<ValidationCheckModel>()

//    var enabled by remember { mutableStateOf(validationCheckModels.all { it.valid }) }

    // we use this variable to know when to show the validation errors to user as the errors should
    // not be show if it is the first time user sees the screen
    var shouldShowValidationError by remember {
        mutableStateOf(false)
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

    Scaffold(Modifier.padding(all = 32.dp),
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(modifier = Modifier.weight(0.5f), onClick = {
                    previousScreen.invoke()
                }) {
                    Text(text = "previous")
                }
                Spacer(modifier = Modifier.width(24.dp))
                Button(modifier = Modifier.weight(0.5f), /*enabled = enabled,*/
                    onClick = {
                        if (validationCheckModels.all {
                                it.valid
                            }
                        ) {
                            nextScreen.invoke()
                        } else {
                            shouldShowValidationError = true
                        }
                    }
                ) {
                    Text(text = "next")
                }
            }
        }) {
        screenConfigs.getOrNull(currentScreen)?.let { config ->

            // first we check if the screen dependency condition is true as the screen is either visible or invisible by dependency condition
            if (dependencyHandler(
                    dependencies = config.dependencies, formValue = formValueState
                )
            ) Screen(
                paddingValues = it,
                screenConfig = config,
                formValue = formValueState,
                onValueChange = onValueChange,
                validationCheckModels = validationCheckModels,
                shouldShowValidationError = shouldShowValidationError
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
                Log.d("TAGTAG", formValueState.toMap().toString())
            }) {
                Text(text = "SAVE")
            }
        }
    }
}

@Preview
@Composable
fun PreviewForm() {
    FormGeneratorTheme {
        Form(viewModel = MainViewModel())
    }
}