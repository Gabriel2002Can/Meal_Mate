package com.example.mealmate.data.Models


data class InstructionDetailed(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    val equipment: List<Equipment>,
    val length: Length?
)

data class Equipment(
    val id: Int,
    val name: String,
    val localizedName: String,
    val image: String
)

data class Length(
    val number: Int,
    val unit: String
)