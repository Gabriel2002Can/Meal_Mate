package com.example.mealmate

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mealmate.ui.theme.MealMateTheme
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mealmate.ui.theme.CustomBackgroundColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the splash screen
        val splashScreen = installSplashScreen()

        // Display splash screen for 2 seconds
        splashScreen.setKeepOnScreenCondition {
            Handler(Looper.getMainLooper()).postDelayed({
                splashScreen.setKeepOnScreenCondition { false }  // Stop keeping it on screen
            }, 2000)
            true
        }

        enableEdgeToEdge()
        setContent {
            MealMateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TermsAndConditions(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TermsAndConditions(modifier: Modifier = Modifier) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().background(CustomBackgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {



            Column(
                modifier = Modifier.weight(1f).padding(16.dp)
            ) {

                Text(
                    text = buildAnnotatedString {
                        append("Terms and Conditions\n\n")
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append("Introduction\n")
                        pop()

                        append("Welcome to Meal Mate! These Terms and Conditions (\"Terms\") govern your access to and use of the Meal Mate application (\"App\"). By using the App, you agree to comply with and be bound by these Terms. Please read them carefully before using the App.\n")
                        append("\n")

                        append("1. ")
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append("User Agreement\n")
                        pop()

                        append("By accessing and using Meal Mate, you agree to these Terms and Conditions, as well as any additional guidelines or rules that may apply to specific features or services provided through the App.\n")
                    },
                    modifier = Modifier.padding(16.dp).weight(1f),
                    color = Color.White
                )

                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Accept Terms and Conditions")
                }
            }
        }
    }
}
