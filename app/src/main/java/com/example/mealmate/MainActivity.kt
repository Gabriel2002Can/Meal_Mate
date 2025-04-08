package com.example.mealmate

import android.os.Bundle
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mealmate.ui.theme.MealMateTheme
import androidx.compose.material3.Button
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
import androidx.room.Room
import com.example.mealmate.database.RecipeDatabase
import com.example.mealmate.remote.ApiInterface
import com.example.mealmate.remote.RecipeRepository
import com.example.mealmate.ui.theme.CustomBackgroundColor
import com.example.mealmate.ui.theme.compose.RecipeScreen
import com.example.mealmate.ui.theme.viewmodel.RecipeViewModel
import kotlinx.coroutines.delay
import com.example.mealmate.ui.theme.compose.RecipeDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = ApiInterface.create()
        val db = Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
        ).build()

        val dao = db.recipeDao()
        val repository = RecipeRepository(api, dao)

        val viewModel = RecipeViewModel(repository)

        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            MealMateTheme {
                var showSplash by remember { mutableStateOf(true) }
                var selectedRecipeId by remember { mutableStateOf<Int?>(null) }
                var acceptedTerms by remember { mutableStateOf(false) }


                //Delay 2 seconds then show main screen
                LaunchedEffect(Unit) {
                    delay(2000)
                    showSplash = false
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when {
                            !acceptedTerms -> {
                                TermsAndConditions(onAccept = {
                                    acceptedTerms = true
                                })
                            }
                            selectedRecipeId != null -> {
                                RecipeDetailScreen(
                                    viewModel = viewModel,
                                    recipeId = selectedRecipeId!!
                                ) {
                                    selectedRecipeId = null // go back to list
                                }
                            }
                            else -> {
                                RecipeScreen(
                                    viewModel = viewModel,
                                    onRecipeSelected = { recipeId ->
                                        selectedRecipeId = recipeId
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TermsAndConditions(
    modifier: Modifier = Modifier,
    onAccept: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().background(CustomBackgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(1f).padding(16.dp)) {
                Text(
                    text = buildAnnotatedString {
                        append("Terms and Conditions\n\n")
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append("Introduction\n")
                        pop()
                        append("Welcome to Meal Mate... etc")
                    },
                    modifier = Modifier.padding(16.dp).weight(1f),
                    color = Color.White
                )

                Button(
                    onClick = { onAccept() }, // now it triggers the state change
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Accept Terms and Conditions")
                }
            }
        }
    }
}
