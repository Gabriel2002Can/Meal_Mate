package com.example.mealmate.ui.theme.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.data.Models.RecipeDetails
import com.example.mealmate.remote.RecipeRepository


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel : ViewModel() {

    private val repository = RecipeRepository()

    private val _recipes = mutableStateOf<List<RecipeDetails>>(emptyList())
    val recipes: State<List<RecipeDetails>> = _recipes

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    fun searchRecipes(query: String) {
        repository.getRecipe(query).enqueue(object : Callback<MultipleRecipes> {
            override fun onResponse(
                call: Call<MultipleRecipes>,
                response: Response<MultipleRecipes>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _recipes.value = response.body()!!.results
                } else {
                    _error.value = "Something went wrong: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<MultipleRecipes>, t: Throwable) {
                _error.value = "Failed: ${t.message}"
            }
        })
    }

    private val _selectedRecipeDetails = mutableStateOf<RecipeDetails?>(null)
    val selectedRecipeDetails: State<RecipeDetails?> = _selectedRecipeDetails

    fun fetchRecipeDetails(id: Int) {
        repository.getRecipeDetails(id).enqueue(object : Callback<RecipeDetails> {
            override fun onResponse(call: Call<RecipeDetails>, response: Response<RecipeDetails>) {
                if (response.isSuccessful && response.body() != null) {
                    _selectedRecipeDetails.value = response.body()
                } else {
                    _error.value = "Something went wrong: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<RecipeDetails>, t: Throwable) {
                _error.value = "Failed: ${t.message}"
            }
        })
    }
}
