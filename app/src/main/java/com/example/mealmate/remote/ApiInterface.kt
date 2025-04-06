package com.example.mealmate.remote

import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.data.Models.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    //Multiple
    //  Api key
    @Headers("x-api-key: 600bb229edff4606877633f91de9a3d7")

    //Multiple recipes
    @GET("recipes/complexSearch")
    fun getRecipes(
        @Query("query") query: String
    ): Call<MultipleRecipes>
}