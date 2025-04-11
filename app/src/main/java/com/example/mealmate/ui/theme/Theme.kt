package com.example.mealmate.ui.theme

import android.app.Activity
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

val CustomBackgroundColor = Color(0xFFE1440F)

private val DarkColorScheme = darkColorScheme(

    //Black
    primary = Color(0xFF191616),

    //Light Orange
    secondary = Color(0xFFF2D3C8),

    tertiary = Pink80
)

@Composable
fun MealMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val LightColorScheme = lightColorScheme(
        //Light Orange
        primary = Color(0xFFF2D3C8),

        //"White
        secondary = Color(0xFFFFF3EF),
        tertiary = Pink40,
        background = CustomBackgroundColor,  //Light background color for light theme
        surface = Color.White            //Surface color to match
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