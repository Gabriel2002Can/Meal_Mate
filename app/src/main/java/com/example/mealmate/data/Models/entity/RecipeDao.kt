package com.example.mealmate.data.Models.entity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("DELETE FROM recipes")
    suspend fun clearAll()

    @Query("SELECT * FROM recipes WHERE searchQuery = :query")
    suspend fun getRecipesByName(query: String): List<RecipeEntity>
}