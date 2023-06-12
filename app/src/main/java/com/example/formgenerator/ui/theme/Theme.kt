package com.example.formgenerator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = darkPrimary,
    secondary = darkSecondary,
    background = darkBackground,
    surface = darkBackground,
    onPrimary = darkOnPrimary,
    onSecondary = darkOnSecondary,
    onBackground = darkOnBackground,
    onSurface = darkOnBackground,
    error = darkError,
)

private val LightColorPalette = lightColors(
    primary = lightPrimary,
    secondary = lightSecondary,
    background = lightBackground,
    surface = lightBackground,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = lightOnBackground,
    onSurface = lightOnBackground,
    error = lightError,
)

@Composable
fun FormGeneratorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}