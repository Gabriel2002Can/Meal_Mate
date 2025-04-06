package com.example.mealmate.remote
import retrofit2.Call
import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.remote.RetrofitInstance



class RecipeRepository {
    fun getRecipe(query: String): Call<MultipleRecipes> {
        return RetrofitInstance.api.getRecipes(query)
    }
}