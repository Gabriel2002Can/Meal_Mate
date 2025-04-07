package com.example.mealmate.remote

import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.data.Models.RecipeDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    //  Api key
    @Headers("x-api-key: 600bb229edff4606877633f91de9a3d7")

    //Multiple recipes
    @GET("recipes/complexSearch")
    fun getRecipes(
        @Query("query") query: String
    ): Call<MultipleRecipes>;

    //Single recipe
    @GET("recipes/{id}/information")
    fun getRecipeDetails(
        @Path("id") id: Int
    ): Call<RecipeDetails>;
}