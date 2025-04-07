package com.example.mealmate.ui.theme.viewmodel
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

    private val _selectedRecipeDetails = mutableStateOf<RecipeDetails?>(null)
    val selectedRecipeDetails: State<RecipeDetails?> = _selectedRecipeDetails

    fun fetchRecipeDetails(id: Int) {
        repository.getRecipeDetails(id).enqueue(object : Callback<RecipeDetails> {
            override fun onResponse(
                call: Call<RecipeDetails>,
                response: Response<RecipeDetails>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _selectedRecipeDetails.value = response.body()
                } else {
                    _error.value = "Failed to load details: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<RecipeDetails>, t: Throwable) {
                _error.value = "Error loading details: ${t.message}"
            }
        })
    }
}
