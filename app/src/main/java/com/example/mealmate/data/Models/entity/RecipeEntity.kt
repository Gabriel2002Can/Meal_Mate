package com.example.mealmate.data.Models.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mealmate.data.Models.ExtendedIngredient
import com.example.mealmate.data.Models.InstructionDetailed

// This is the Room database model

@Entity(tableName = "recipes")
    data class RecipeEntity(
        @PrimaryKey val id: Int,
        val aggregateLikes: Int = 0,
        val analyzedInstructions: List<InstructionDetailed> = emptyList(), // Converted with TypeConverter
        val cheap: Boolean = false,
        val cookingMinutes: Int = 0,
        val creditsText: String = "",
        val cuisines: List<String> = emptyList(), // Converted
        val dairyFree: Boolean = false,
        val diets: List<String> = emptyList(), // Converted
        val dishTypes: List<String> = emptyList(), // Converted
        // Do not include directly here since it is stored in a separate table, ExtendedIngredientsEntity with a foreign key
        // val extendedIngredients: List<ExtendedIngredient> = emptyList(),
        val gaps: String = "",
        val glutenFree: Boolean = false,
        val healthScore: Int = 0,
        val image: String,
        val imageType: String = "",
        val instructions: String = "",
        val license: String = "",
        val lowFodmap: Boolean = false,
        //val nutrition: Nutrition = Nutrition(...),
        val occasions: List<String> = emptyList(), // Converted
        val originalId: Int = 0,
        val preparationMinutes: Int = 0,
        val pricePerServing: Double = 0.0,
        val readyInMinutes: Int = 0,
        val servings: Int = 0,
        val sourceName: String = "",
        val sourceUrl: String = "",
        val spoonacularScore: Double = 0.0,
        val spoonacularSourceUrl: String = "",
        val summary: String = "",
        val sustainable: Boolean = false,
        val title: String,
        val vegan: Boolean = false,
        val vegetarian: Boolean = false,
        val veryHealthy: Boolean = false,
        val veryPopular: Boolean = false,
        val weightWatcherSmartPoints: Int = 0,
        val searchQuery: String = ""     //Reference string to the database
)

