package com.example.mealmate.ui.theme.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mealmate.ui.theme.viewmodel.RecipeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mealmate.data.Models.entity.RecipeEntity
import com.example.mealmate.ui.theme.Orange
import com.example.mealmate.ui.theme.lightOrange
import com.example.mealmate.ui.theme.offWhite
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel,
    onRecipeSelected: (Int) -> Unit,
    navController: NavController
) {
    var query by remember { mutableStateOf("") }
    val recipes by viewModel.recipes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightOrange)
    )
    {
        // Add TopAppBar at the top of the screen
        TopAppBar(
            title = {
                Text(
                    text = "Recipes",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Orange),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        // User enters their search term
        OutlinedTextField(
            // Update the query value as the user types
            value = query,
            onValueChange = { query = it },

            // Search bar styling
            label = { Text("Search Recipes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp) // Padding above search bar
                .padding(horizontal = 16.dp), // Padding on sides of search bar
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = offWhite
            ),
            singleLine = true,

            // Trigger viewModel.searchRecipes(query) when user clicks the search icon
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.searchRecipes(query)
                    Log.d("RecipeScreen", "Search triggered with query: $query")
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (recipes.isEmpty()) {
                Box(
                    Modifier.fillMaxSize().background(lightOrange),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No recipes found or still loading...")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(recipes) { recipe ->
                        RecipeItem(recipe = recipe) {
                            //onRecipeSelected(recipe.id)
                            navController.navigate("recipeDetail/${recipe.id}") // replace with this
                        }
                    }
                }
            }
        }

        BottomNavBar(navController = navController)
    }
}

@Composable
fun RecipeItem(recipe: RecipeEntity, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .background(lightOrange)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            //Displaying the recipe image
            Image(
                painter = rememberAsyncImagePainter(recipe.image),
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop, // Fill the circle shape
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .size(100.dp)
                    .clip(CircleShape)
            )

            //Displaying the recipe title
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(viewModel: RecipeViewModel, navController: NavController, recipeId: Int, onBack: () -> Unit) {
    val recipe = viewModel.selectedRecipe.value
    val ingredients = viewModel.ingredients.value  // Ingredients state from the ViewModel

    // Load recipe details and ingredients when recipeId changes
    // THIS IS PROBABLY WRONG???
    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetails(recipeId)
        viewModel.loadIngredients(recipeId) // Load ingredients as well
    }

    Scaffold(
        // Display top app bar and back button
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Recipe",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Orange),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        },
        // Display bottom nav bar
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        content = { paddingValues ->
            // Display background color
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(lightOrange)
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                // THIS IF BLOCK IS NOT RUNNING AND I DO NOT UNDERSTAND WHY - HOW IS RECIPE NULL?
                if (recipe != null) {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            // Image
                            AsyncImage(
                                model = recipe.image,
                                contentDescription = recipe.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .padding(top = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Title
                            Text(
                                text = recipe.title,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Summary
                            Text(
                                text = HtmlCompat.fromHtml(recipe.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
                                    .toString()
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Ingredients
                            Text(text = "Ingredients:", style = MaterialTheme.typography.titleMedium)
                            if (ingredients.isNotEmpty()) {
                                ingredients.forEach { ingredient ->
                                    Text(text = "• ${ingredient.amount} ${ingredient.unit} ${ingredient.name}")
                                }
                            } else {
                                Text(text = "No ingredients provided.")
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            // Instructions
                            Text(text = "Instructions:", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = HtmlCompat.fromHtml(
                                    recipe.instructions.ifBlank { "No instructions provided." },
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                ).toString()
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    )
}

//Loading indicator
//?: run {
//    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        CircularProgressIndicator()
//    }
//}