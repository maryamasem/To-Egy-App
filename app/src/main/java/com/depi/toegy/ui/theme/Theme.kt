package com.depi.toegy.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = Navy,
    secondary = AccentYellow,
    background = LightBackground,
    surface = CardWhite
)

@Composable
fun ToEgyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
