package com.example.mealmate.database

import androidx.room.TypeConverter
import com.example.mealmate.data.Models.ExtendedIngredient
import com.example.mealmate.data.Models.InstructionDetailed
import com.example.mealmate.data.Models.Nutrition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromExtendedIngredientList(value: List<ExtendedIngredient>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toExtendedIngredientList(value: String): List<ExtendedIngredient> {
        return gson.fromJson(value, object : TypeToken<List<ExtendedIngredient>>() {}.type)
    }

    @TypeConverter
    fun fromNutrition(value: Nutrition?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toNutrition(value: String): Nutrition {
        return gson.fromJson(value, Nutrition::class.java)
    }

    @TypeConverter
    fun fromAny(value: Any?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAny(value: String): Any? {
        return gson.fromJson(value, Any::class.java)
    }

    @TypeConverter
    fun fromInstructionList(value: List<InstructionDetailed>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<InstructionDetailed>>() {}.type
        return gson.toJson(value, type)
    }

    // Convert JSON String back to List<InstructionDetailed>
    @TypeConverter
    fun toInstructionList(value: String): List<InstructionDetailed> {
        val gson = Gson()
        val type = object : TypeToken<List<InstructionDetailed>>() {}.type
        return gson.fromJson(value, type)
    }
}
