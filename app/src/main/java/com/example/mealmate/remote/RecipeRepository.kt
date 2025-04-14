package com.example.mealmate.remote
import android.util.Log
import retrofit2.Call
import com.example.mealmate.data.Models.RecipeDetails
import com.example.mealmate.data.Models.entity.ExtendedIngredientEntity
import com.example.mealmate.database.RecipeDao
import com.example.mealmate.data.Models.entity.RecipeEntity
import retrofit2.Response

class RecipeRepository(
    // Takes two dependencies via constructor injection
    private val api: ApiInterface, // The interface used to make requests to the API
    val dao: RecipeDao, // The data access object for Room database operations
) {
    // Used when user performs a search
    // Displays recipes with basic info (title and image)
    suspend fun getRecipes(query: String): List<RecipeEntity> {
        Log.d("RecipeRepository", "getRecipes called with query: $query")

        try {
            Log.d("RecipeRepository", "About to make API call")
            val response = api.getRecipes(query)
            Log.d("RecipeRepository", "API call completed")
            Log.d("RecipeRepository", "API response code: ${response.code()}")
            Log.d("RecipeRepository", "API response: ${response.body()}")

            // If response is successful and not null
            if (response.isSuccessful && response.body() != null) {
                // Map each recipe from the API into a RecipeEntity
                val fetchedRecipes = response.body()!!.results.map {
                    RecipeEntity(
                        id = it.id,
                        title = it.title,
                        image = it.image ?: "",
                        searchQuery = query
                    )
                }

                // Save each fetched recipe in the database (if needed)
                fetchedRecipes.forEach {
                    dao.insertRecipe(it)
                }

                // Return the fetched recipes
                return fetchedRecipes
            }
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Exception during API call", e)
            e.printStackTrace() // This will print the full stack trace
            return emptyList()
        }

        // If nothing was found, return an empty list
        return emptyList()
    }

    // Function to get a recipe by its ID
    suspend fun getRecipeById(id: Int): RecipeEntity? {
        return dao.getRecipeById(id)
    }

    // Users when a user clicks on a specific recipe
    // Displays full details for the recipe
    suspend fun getOrFetchRecipeDetails(id: Int): RecipeEntity? {
     return try{
            // Make an API call to get the recipe details
            val response = api.getRecipeDetails(id)

            // If call is successful, extract the body as 'details'
            if (response.isSuccessful && response.body() != null) {
                val details = response.body()!!

                // Create an updated RecipeEntity by copying the original cached entity and updating it with full API details
                val recipe = RecipeEntity(
                    id = details.id,
                    aggregateLikes = details.aggregateLikes,
                    analyzedInstructions = details.analyzedInstructions,
                    cheap = details.cheap,
                    cookingMinutes = details.cookingMinutes,
                    creditsText = details.creditsText,
                    cuisines = details.cuisines,
                    dairyFree = details.dairyFree,
                    diets = details.diets,
                    dishTypes = details.dishTypes,
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
                    title = details.title,
                    vegan = details.vegan,
                    vegetarian = details.vegetarian,
                    veryHealthy = details.veryHealthy,
                    veryPopular = details.veryPopular,
                    weightWatcherSmartPoints = details.weightWatcherSmartPoints,
                )

                // Map a list of ingredients from the API to ExtendedIngredientEntity
                val ingredients = details.extendedIngredients.map { ingredient ->
                    ExtendedIngredientEntity(
                        id = 0,
                        ingredientsId = ingredient.id,
                        recipeId = id,
                        name = ingredient.name,
                        amount = ingredient.amount,
                        unit = ingredient.unit,
                        aisle = ingredient.aisle
                    )
                }

                // Save the recipe and ingredients in the database
                dao.insertRecipe(recipe)
                dao.insertIngredients(ingredients)

                // Return the updated recipe if all succeeded, otherwise return null
                recipe
            } else null

        } catch (e: Exception) {
            null
        }
    }
    // Calls the DAO to return ingredients associated with a recipe from the database
    suspend fun getIngredientsForRecipe(recipeId: Int): List<ExtendedIngredientEntity> {
        return dao.getIngredientsForRecipe(recipeId)
    }
}