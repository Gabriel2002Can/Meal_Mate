package com.example.mealmate.remote

import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.data.Models.RecipeDetails
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    //  Api key
    @Headers("x-api-key: 600bb229edff4606877633f91de9a3d7")

    //Multiple recipes
    @GET("recipes/complexSearch")
    suspend fun getRecipes(@Query("query") query: String): Response<MultipleRecipes>

    @Headers("x-api-key: 600bb229edff4606877633f91de9a3d7")
    //Single recipe
    @GET("recipes/{id}/information")
    fun getRecipeDetails(
        @Path("id") id: Int
    ): Call<RecipeDetails>;

    //For Database
    companion object {
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}