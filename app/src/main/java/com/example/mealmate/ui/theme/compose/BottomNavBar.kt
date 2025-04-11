package com.example.mealmate.ui.theme.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.mealmate.ui.theme.CustomBackgroundColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val whiteColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        unselectedIconColor = Color.White,
        selectedTextColor = Color.White,
        unselectedTextColor = Color.White
    )

    Box(modifier = Modifier.height(85.dp)) {
        NavigationBar(
            containerColor = CustomBackgroundColor,
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = false, // You can add logic to highlight the selected item
                onClick = { navController.navigate("home") },
                colors = whiteColors
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.LocalDining, contentDescription = "Recipes") },
                label = { Text("Recipes") },
                selected = false,
                onClick = { navController.navigate("recipes") },
                colors = whiteColors
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Grocery") },
                label = { Text("Grocery") },
                selected = false,
                onClick = { navController.navigate("grocery") },
                colors = whiteColors
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = false,
                onClick = { navController.navigate("profile") },
                colors = whiteColors
            )
        }
    }
}