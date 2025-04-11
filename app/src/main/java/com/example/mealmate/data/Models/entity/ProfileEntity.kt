package com.example.mealmate.data.Models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val email: String,
    val name: String,
    val dob: String
)