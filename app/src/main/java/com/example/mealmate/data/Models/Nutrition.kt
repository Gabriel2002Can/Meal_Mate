package com.example.mealmate.data.Models

data class Nutrition(
    val caloricBreakdown: CaloricBreakdown,
    val flavonoids: List<Flavonoid>,
    val ingredients: List<Ingredient>,
    val nutrients: List<NutrientX>,
    val properties: List<Property>,
    val weightPerServing: WeightPerServing
)