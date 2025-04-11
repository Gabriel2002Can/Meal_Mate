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
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mealmate.database.RecipeDatabase
import com.example.mealmate.remote.ApiInterface
import com.example.mealmate.remote.RecipeRepository
import com.example.mealmate.ui.theme.CustomBackgroundColor
import com.example.mealmate.ui.theme.compose.RecipeScreen
import com.example.mealmate.ui.theme.viewmodel.RecipeViewModel
import kotlinx.coroutines.delay
import com.example.mealmate.ui.theme.compose.RecipeDetailScreen
import com.example.mealmate.ui.theme.compose.TermsAndConditions
import com.example.mealmate.ui.theme.compose.HomeScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mealmate.ui.theme.compose.GroceryScreen
import com.example.mealmate.ui.theme.compose.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = ApiInterface.create()
        val db = Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
        )
            .fallbackToDestructiveMigration() // Add this to handle schema changes
            .build()

        val dao = db.recipeDao()
        val repository = RecipeRepository(api, dao)

        val viewModel = RecipeViewModel(repository)

        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            MealMateTheme {
                var showSplash by remember { mutableStateOf(true) }
                var acceptedTerms by remember { mutableStateOf(false) }
                var selectedRecipeId by remember { mutableStateOf<Int?>(null) }

                val navController = rememberNavController()

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

                            else -> {
                                NavHost(navController = navController, startDestination = "home") {
                                    composable("home") {
                                        HomeScreen(navController = navController)
                                    }
                                    composable("recipes") {
                                        RecipeScreen(
                                            navController = navController,
                                            viewModel = viewModel,
                                            onRecipeSelected = { recipeId ->
                                                navController.navigate("recipeDetail/$recipeId")
                                            }
                                        )
                                    }
                                    composable(
                                        route = "recipeDetail/{recipeId}",
                                        arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                                    ) { backStackEntry ->
                                        val recipeId = backStackEntry.arguments?.getInt("recipeId")
                                        recipeId?.let {
                                            RecipeDetailScreen(
                                                viewModel = viewModel,
                                                recipeId = it,
                                                onBack = { navController.popBackStack() }
                                            )
                                        }
                                    }
                                    composable("grocery") {
                                        GroceryScreen(navController = navController)
                                    }
                                    composable("profile") {
                                        ProfileScreen(navController = navController)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}