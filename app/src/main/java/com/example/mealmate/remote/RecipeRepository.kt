package com.example.mealmate.remote
import retrofit2.Call
import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.data.Models.RecipeDetails


class RecipeRepository {

    //For Multiple recipes; https://api.spoonacular.com/recipes/complexSearch
    fun getRecipe(query: String): Call<MultipleRecipes> {
        return RetrofitInstance.api.getRecipes(query)
    }

    //For single recipe information; https://api.spoonacular.com/recipes/{id}/information
    fun getRecipeDetails(id: Int): Call<RecipeDetails> {
        return RetrofitInstance.api.getRecipeDetails(id)
    }
}