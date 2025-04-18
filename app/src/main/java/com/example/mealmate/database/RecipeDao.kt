package com.example.mealmate.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mealmate.data.Models.entity.ExtendedIngredientEntity
import com.example.mealmate.data.Models.entity.RecipeEntity

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

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    suspend fun getRecipeById(id: Int): RecipeEntity?

    // Insert ingredients for a recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<ExtendedIngredientEntity>) // ingredient or ingredients??

    // Get ingredients for a specific recipe
    @Query("SELECT * FROM extended_ingredients WHERE recipeId = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Int): List<ExtendedIngredientEntity>
}