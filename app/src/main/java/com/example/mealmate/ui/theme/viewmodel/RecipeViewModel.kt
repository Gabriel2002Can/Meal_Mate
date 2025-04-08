package com.example.mealmate.ui.theme.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.data.Models.RecipeDetails
import com.example.mealmate.data.Models.entity.RecipeEntity
import com.example.mealmate.remote.RecipeRepository
import kotlinx.coroutines.launch


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes = mutableStateOf<List<RecipeEntity>>(emptyList())
    val recipes: State<List<RecipeEntity>> = _recipes

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.getRecipes(query)
                if (result.isNotEmpty()) {
                    _recipes.value = result
                } else {
                    _error.value = "No recipes found for \"$query\"."
                }
            } catch (e: Exception) {
                _error.value = "Something went wrong: ${e.message}"
            }
        }
    }

    private val _selectedRecipe = mutableStateOf<RecipeEntity?>(null)
    val selectedRecipe: State<RecipeEntity?> = _selectedRecipe

    fun loadRecipeDetails(id: Int) {
        viewModelScope.launch {
            try {
                // First check local DB
                val cachedRecipe = repository.dao.getRecipeById(id)
                if (cachedRecipe != null && cachedRecipe.instructions.isNotEmpty()) {
                    _selectedRecipe.value = cachedRecipe
                } else {
                    val response = repository.getRecipeDetails(id).execute()
                    if (response.isSuccessful) {
                        response.body()?.let { details ->
                            val updatedRecipe = cachedRecipe?.copy(
                                aggregateLikes = details.aggregateLikes,
                                analyzedInstructions = details.analyzedInstructions,
                                cheap = details.cheap,
                                cookingMinutes = details.cookingMinutes,
                                creditsText = details.creditsText ?: "",
                                cuisines = details.cuisines,
                                dairyFree = details.dairyFree,
                                diets = details.diets,
                                dishTypes = details.dishTypes,
                                extendedIngredients = details.extendedIngredients,
                                gaps = details.gaps ?: "",
                                glutenFree = details.glutenFree,
                                healthScore = details.healthScore,
                                image = details.image,
                                imageType = details.imageType,
                                instructions = details.instructions ?: "",
                                license = details.license ?: "",
                                lowFodmap = details.lowFodmap,
                                occasions = details.occasions,
                                originalId = details.originalId,
                                preparationMinutes = details.preparationMinutes,
                                pricePerServing = details.pricePerServing,
                                readyInMinutes = details.readyInMinutes,
                                servings = details.servings,
                                sourceName = details.sourceName ?: "",
                                sourceUrl = details.sourceUrl ?: "",
                                spoonacularScore = details.spoonacularScore,
                                spoonacularSourceUrl = details.spoonacularSourceUrl ?: "",
                                summary = details.summary ?: "",
                                sustainable = details.sustainable,
                                vegan = details.vegan,
                                vegetarian = details.vegetarian,
                                veryHealthy = details.veryHealthy,
                                veryPopular = details.veryPopular,
                                weightWatcherSmartPoints = details.weightWatcherSmartPoints,
                            )

                            if (updatedRecipe != null) {
                                repository.insertRecipe(updatedRecipe)
                                _selectedRecipe.value = updatedRecipe
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error loading recipe details: ${e.message}")
            }
        }
    }
}
