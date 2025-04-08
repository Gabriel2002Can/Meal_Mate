package com.example.mealmate.remote
import android.util.Log
import retrofit2.Call
import com.example.mealmate.data.Models.RecipeDetails
import com.example.mealmate.data.Models.entity.RecipeDao
import com.example.mealmate.data.Models.entity.RecipeEntity


class RecipeRepository(
    private val api: ApiInterface,
    val dao: RecipeDao
) {
    private val TAG = "RecipeRepository" // Set a log tag for your repository

    // Check database first, then API
    suspend fun getRecipes(query: String): List<RecipeEntity> {
        Log.d(TAG, "Searching for recipes with query: $query")

        val cachedRecipes = dao.getRecipesByName(query)
        if (cachedRecipes.isNotEmpty()) {
            Log.d(TAG, "Found ${cachedRecipes.size} recipes in the local database.")
            return cachedRecipes
        } else {
            Log.d(TAG, "No recipes found in the local database, calling the API.")

            try {
                val response = api.getRecipes(query)

                Log.d(TAG, "API raw response: ${response.raw()}")
                Log.d(TAG, "API response code: ${response.code()}, message: ${response.message()}")

                if (response.isSuccessful && response.body() != null) {
                    val rawJson = response.body().toString()
                    Log.d(TAG, "Raw JSON body: $rawJson")

                    val fetchedRecipes = response.body()!!.results.map {
                        RecipeEntity(
                            id = it.id,
                            title = it.title,
                            image = it.image ?: "",
                            searchQuery = query
                        )
                    }

                    fetchedRecipes.forEach {
                        Log.d(TAG, "Inserting recipe ${it.title} into the database.")
                        dao.insertRecipe(it)
                    }

                    Log.d(TAG, "Fetched ${fetchedRecipes.size} recipes from the API.")
                    return fetchedRecipes
                } else {
                    Log.e(TAG, "API call failed. Code: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception during API call: ${e.message}", e)
            }

            return emptyList()
        }
    }


    // For single recipe details
    fun getRecipeDetails(id: Int): Call<RecipeDetails> {
        Log.d(TAG, "Fetching details for recipe with id: $id")
        return api.getRecipeDetails(id)
    }

    suspend fun getOrFetchRecipeDetails(id: Int): RecipeEntity? {
        // Try fetching from DB first
        val cached = dao.getRecipeById(id)
        if (cached != null && cached.instructions.isNotBlank()) {
            return cached
        }

        return try {
            val response = api.getRecipeDetails(id).execute()
            if (response.isSuccessful && response.body() != null) {
                val details = response.body()!!
                val updatedRecipe = cached?.copy(
                    aggregateLikes = details.aggregateLikes,
                    analyzedInstructions = details.analyzedInstructions,
                    cheap = details.cheap,
                    cookingMinutes = details.cookingMinutes,
                    creditsText = details.creditsText,
                    cuisines = details.cuisines,
                    dairyFree = details.dairyFree,
                    diets = details.diets,
                    dishTypes = details.dishTypes,
                    extendedIngredients = details.extendedIngredients,
                    gaps = details.gaps,
                    glutenFree = details.glutenFree,
                    healthScore = details.healthScore,
                    image = details.image,
                    imageType = details.imageType,
                    instructions = details.instructions,
                    license = details.license,
                    lowFodmap = details.lowFodmap,
                    occasions = details.occasions,
                    originalId = details.originalId,
                    preparationMinutes = details.preparationMinutes,
                    pricePerServing = details.pricePerServing,
                    readyInMinutes = details.readyInMinutes,
                    servings = details.servings,
                    sourceName = details.sourceName,
                    sourceUrl = details.sourceUrl,
                    spoonacularScore = details.spoonacularScore,
                    spoonacularSourceUrl = details.spoonacularSourceUrl,
                    summary = details.summary,
                    sustainable = details.sustainable,
                    vegan = details.vegan,
                    vegetarian = details.vegetarian,
                    veryHealthy = details.veryHealthy,
                    veryPopular = details.veryPopular,
                    weightWatcherSmartPoints = details.weightWatcherSmartPoints,
                ) ?: return null

                dao.insertRecipe(updatedRecipe)
                updatedRecipe
            } else null
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error fetching details: ${e.message}", e)
            null
        }
    }

    suspend fun insertRecipe(recipe: RecipeEntity) {
        Log.d(TAG, "Inserting recipe ${recipe.title} into the database.")
        dao.insertRecipe(recipe)
    }

    suspend fun getAllRecipes(): List<RecipeEntity> {
        Log.d(TAG, "Fetching all recipes from the local database.")
        return dao.getAllRecipes()
    }
}