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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel


import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mealmate.ui.theme.MealMateTheme
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.room.util.query
import com.example.mealmate.ui.theme.CustomBackgroundColor
import com.example.mealmate.ui.theme.viewmodel.RecipeViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            MealMateTheme {
                var showSplash by remember { mutableStateOf(true) }

                // Delay 2 seconds then show main screen
                LaunchedEffect(Unit) {
                    delay(2000)
                    showSplash = false
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        if (showSplash) {
                            TermsAndConditions()
                        } else {
                            RecipeScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeScreen(viewModel: RecipeViewModel = viewModel()) {
    val recipes by viewModel.recipes
    val error by viewModel.error

    var query by remember { mutableStateOf("") } //

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search recipes") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.searchRecipes(query) }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            Text(text = error, color = Color.Red)
        }

        LazyColumn {
            items(recipes) { recipe ->
                Text(recipe.title ?: "recipe test")
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
