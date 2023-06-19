package com.example.formgenerator.ui.view

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import com.google.gson.GsonBuilder

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

    var progress by remember {
        mutableStateOf(1f / screenConfigs.size.toFloat())
    }

    // as there might be a dependency between screens which may lead to a screen being invisible
    // we keep track of the last screen
    var lastScreen by remember {
        mutableStateOf(0)
    }

    // we use this list to see if there is any invalid widget
    val validationCheckModels = mutableListOf<ValidationCheckModel>()

    // we use this variable to know when to show the validation errors to user as the errors should
    // not be show if it is the first time user sees the screen
    var shouldShowValidationError by remember {
        mutableStateOf(false)
    }

    val nextScreen = {
        if (currentScreen < screenConfigs.size) {
            lastScreen = currentScreen
            currentScreen++
            progress = (currentScreen + 1).toFloat() / screenConfigs.size.toFloat()
        }
    }

    val previousScreen = {
        if (currentScreen > 0) {
            lastScreen = currentScreen
            currentScreen--
            progress = (currentScreen + 1).toFloat() / screenConfigs.size.toFloat()
        }
    }

    // the lambda which is being used to update the form value
    val onValueChange: (Map<String, Any?>) -> Unit = {
        formValueState[it.keys.first().toString()] = it[it.keys.first()]
    }

    Scaffold(
        modifier = Modifier,
        topBar = { ScreenTopAppBar(progress = progress) },
        bottomBar = {
            BottomAppBar(onPreviousScreenButtonClick = {
                previousScreen.invoke()
            }, onNextScreenButtonClick = {
                if (validationCheckModels.all {
                        it.valid
                    }
                ) {
                    shouldShowValidationError = false
                    nextScreen.invoke()
                } else {
                    shouldShowValidationError = true
                }
            })
        }) {
        screenConfigs.getOrNull(currentScreen)?.let { config ->

            // first we check if the screen dependency condition is true as the screen is either
            // visible or invisible by dependency condition
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
            // as the validation is checked before when next is clicked we do not check it here
            else {
                if (lastScreen <= currentScreen) {
                    nextScreen.invoke()
                }
                if (lastScreen > currentScreen) {
                    previousScreen.invoke()
                }
            }
        }

        // save screen:
        if (currentScreen == screenConfigs.size) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            BasicTextField(
                modifier = Modifier.fillMaxSize(),
                value = gson.toJson(formValueState.toMap()),
                onValueChange = {})
        }
    }
}

@Composable
fun ScreenTopAppBar(progress: Float) {
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
    )
    Column {
        TopAppBar(contentPadding = PaddingValues(start = 16.dp)) {
            Text(text = "Form Title")
        }
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = progressAnimation,
            color = Color.Blue
        )
    }
}

@Composable
fun BottomAppBar(
    onNextScreenButtonClick: () -> Unit,
    onPreviousScreenButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(modifier = Modifier.weight(0.5f), onClick = {
            onPreviousScreenButtonClick.invoke()
        }) {
            Text(text = "previous")
        }
        Spacer(modifier = Modifier.width(24.dp))
        Button(modifier = Modifier.weight(0.5f),
            onClick = {
                onNextScreenButtonClick.invoke()
            }
        ) {
            Text(text = "next")
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