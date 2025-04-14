package com.example.mealmate.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mealmate.data.Models.entity.Converters
import com.example.mealmate.data.Models.entity.ExtendedIngredientEntity
import com.example.mealmate.data.Models.entity.RecipeEntity
import com.example.mealmate.data.Models.entity.ProfileEntity

@Database(
    entities = [
        RecipeEntity::class,
        ProfileEntity::class,
        ExtendedIngredientEntity::class
    ],
    version = 7
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_4_5, MIGRATION_5_6)
                    .build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE recipes ADD COLUMN searchQuery TEXT DEFAULT ''")
    }
}

// New migration to add the extended_ingredients table
private val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create the extended_ingredients table
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS extended_ingredients (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                recipeId INTEGER NOT NULL,
                aisle TEXT NOT NULL,
                amount REAL NOT NULL,
                name TEXT NOT NULL,
                unit TEXT NOT NULL,
                FOREIGN KEY(recipeId) REFERENCES recipes(id) ON DELETE CASCADE
            )
        """)
    }
}

// Newer migration
private val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Drop the old table if it exists
        database.execSQL("DROP TABLE IF EXISTS extended_ingredients")

        // Create the table with your simplified schema
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS extended_ingredients (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                recipeId INTEGER NOT NULL,
                aisle TEXT NOT NULL,
                amount REAL NOT NULL,
                name TEXT NOT NULL,
                unit TEXT NOT NULL,
                FOREIGN KEY(recipeId) REFERENCES recipes(id) ON DELETE CASCADE
            )
        """)
    }
}