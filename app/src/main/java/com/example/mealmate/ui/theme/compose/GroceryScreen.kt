package com.example.mealmate.ui.theme.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmate.ui.theme.Orange
import com.example.mealmate.ui.theme.lightOrange
import com.example.mealmate.ui.theme.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RecipeViewModel
) {
    // Track selected items
    val selectedItems = remember { mutableStateListOf<String>() }

    // Access the grocery list from the ViewModel
    val groceryList = viewModel.groceryList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightOrange),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Grocery List",
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

            if (groceryList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(groceryList) { ingredientName ->  // Use groceryList directly from the ViewModel
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Checkbox(
                                checked = selectedItems.contains(ingredientName),
                                onCheckedChange = {
                                    if (it) selectedItems.add(ingredientName)
                                    else selectedItems.remove(ingredientName)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Orange
                                )
                            )

                            Text(
                                text = ingredientName,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = {
                                    selectedItems.remove(ingredientName)
                                    viewModel.removeItemFromGroceryList(ingredientName)  // Call ViewModel to remove item
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove Item",
                                    tint = Orange
                                )
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your grocery list is empty.",
                        color = Color.Gray
                    )
                }
            }
        }

        BottomNavBar(navController = navController)
    }
}
