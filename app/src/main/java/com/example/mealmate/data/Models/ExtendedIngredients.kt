package com.example.mealmate.data.Models

import androidx.room.PrimaryKey

// This represents the API model
// id refers to the ingredient's Spoonacular ID, not a room primary key

data class ExtendedIngredient(
    val id: Int = 0, // Removed the primary key here
    val aisle: String,
    val amount: Double,
    val name: String,
    val unit: String
)