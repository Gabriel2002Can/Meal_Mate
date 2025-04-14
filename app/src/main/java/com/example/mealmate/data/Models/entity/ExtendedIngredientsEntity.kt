package com.example.mealmate.data.Models.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// This is the room database model

@Entity(
    tableName = "extended_ingredients",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class ExtendedIngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Local Room ID
    val ingredientsId: Int, // Spoonacular ingredient ID
    val recipeId: Int, // Foreign key to RecipeEntity
    val aisle: String,
    val amount: Double,
    val name: String,
    val unit: String,
)