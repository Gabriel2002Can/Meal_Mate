package com.example.mealmate.ui.theme.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.data.Models.Recipe
import com.example.mealmate.remote.RecipeRepository


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel : ViewModel() {

    private val repository = RecipeRepository()

    private val _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

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
}
