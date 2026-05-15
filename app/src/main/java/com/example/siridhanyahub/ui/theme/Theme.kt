package com.example.siridhanyahub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(

    primary = Green40,
    secondary = GreenGrey40,
    tertiary = LightGreen40,

    background = Color(0xFFF1F8E9),
    surface = Color.White
)

@Composable
fun SiriDhanyaHubTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}