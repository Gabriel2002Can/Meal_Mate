package com.example.mealmate.ui.theme.compose

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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mealmate.data.Models.entity.RecipeEntity
import com.example.mealmate.ui.theme.CustomBackgroundColor
import com.example.mealmate.ui.theme.primaryLight
import com.example.mealmate.ui.theme.secondaryLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel,
    onRecipeSelected: (Int) -> Unit,
    navController: NavController
) {
    var query by remember { mutableStateOf("") }
    val recipes = viewModel.recipes.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryLight)
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
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = CustomBackgroundColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Recipes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp) // Padding above search bar
                .padding(horizontal = 16.dp) // Padding on sides of search bar
                .background(secondaryLight),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.searchRecipes(query)
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
                    Modifier.fillMaxSize().background(primaryLight),
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
                            onRecipeSelected(recipe.id)
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
            .background(primaryLight)
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

@Composable
fun RecipeDetailScreen(viewModel: RecipeViewModel, recipeId: Int, onBack: () -> Unit) {
    val recipe = viewModel.selectedRecipe.value

    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetails(recipeId)
    }

    recipe?.let {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            Button(onClick = onBack) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(12.dp))

            AsyncImage(
                model = it.image,
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            //Tile
            Text(text = it.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            //Summary
            Text(text = HtmlCompat.fromHtml(it.summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Instructions:", style = MaterialTheme.typography.titleMedium)
            Text(text = it.instructions.ifBlank { "No instructions provided." })
        }

        //Loading indicator
    } ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}