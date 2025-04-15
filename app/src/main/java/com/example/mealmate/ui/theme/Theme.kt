package com.example.mealmate.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val Orange = Color(0xFFE1440F)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF191616),
    secondary = Color(0xFFF2D3C8),
    background = Color(0xFF121212) // Dark grey background for dark mode
)


@Composable
fun MealMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val LightColorScheme = lightColorScheme(
        primary = Color(0xFFF2D3C8),
        secondary = Color(0xFFFFF3EF),
        background = Color(0xFFF2D3C8), // lightOrange for light mode
        surface = Color.White
    )
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}