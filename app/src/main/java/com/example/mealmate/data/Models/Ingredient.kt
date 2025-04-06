package com.example.mealmate.data.Models

data class Ingredient(
    val amount: Double,
    val id: Int,
    val name: String,
    val nutrients: List<NutrientX>,
    val unit: String
)