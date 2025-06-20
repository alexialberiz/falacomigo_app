package com.example.falecomigo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Vinho200,
    primaryVariant = Vinho700,
    secondary = Vinho400
)

private val LightColorPalette = lightColors(
    primary = Vinho500,
    primaryVariant = Vinho700,
    secondary = Vinho400
)

@Composable
fun FaleComigoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}