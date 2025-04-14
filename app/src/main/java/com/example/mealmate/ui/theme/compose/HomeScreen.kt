package com.example.mealmate.ui.theme.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mealmate.ui.theme.Orange
import com.example.mealmate.ui.theme.lightOrange
import com.example.mealmate.ui.theme.viewmodel.RecipeViewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RecipeViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.getRecipeById(631863)
    }

    val featuredRecipe = viewModel.featuredRecipe.value

    if (featuredRecipe != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(lightOrange)
        ) {
            // Top App Bar (Fixed at the top)
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // First card: Featured Recipe
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.35f)
                        .clickable {
                            navController.navigate("recipeDetail/${featuredRecipe.id}")
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(featuredRecipe.image),
                        contentDescription = "Featured Recipe Image",
                        contentScale = ContentScale.Crop, // Fills and crops the image to fit
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(
                        text = "Today's Feature: ${featuredRecipe.title}",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Second card: Grocery list
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            navController.navigate("grocery")
                },
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Grocery List",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally) // This centers the text
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val firstFiveIngredients = viewModel.groceryList.map { it.first }.take(4)

                        firstFiveIngredients.forEach { ingredientName ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Checkbox(
                                    checked = viewModel.selectedItems.contains(ingredientName),
                                    onCheckedChange = {
                                        if (it) viewModel.selectedItems.add(ingredientName)
                                        else viewModel.selectedItems.remove(ingredientName)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Orange
                                    )
                                )

                                Text(
                                    text = ingredientName,
                                    modifier = Modifier.weight(1f),
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal
                                    ),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Navigation Bar (Fixed at the bottom)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BottomNavBar(navController = navController)
            }
        }
    }
}