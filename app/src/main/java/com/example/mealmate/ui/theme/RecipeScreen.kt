package com.example.mealmate.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mealmate.ui.theme.viewmodel.RecipeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.setValue


import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun RecipeScreen(viewModel: RecipeViewModel) {
    val recipes by viewModel.recipes
    val error by viewModel.error

    var query by remember { mutableStateOf("") }

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
                Text(recipe.title ?: "Recipe title missing")
            }
        }
    }
}