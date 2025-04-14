package com.example.mealmate.ui.theme.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.Models.MultipleRecipes
import com.example.mealmate.data.Models.RecipeDetails
import com.example.mealmate.data.Models.entity.ExtendedIngredientEntity
import com.example.mealmate.data.Models.entity.RecipeEntity
import com.example.mealmate.database.RecipeDao
import com.example.mealmate.remote.RecipeRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Define RecipeViewModel class that inherits from built-in ViewModel()
// RecipeRepository is passed in as a dependency to access functions like getRecipes()
class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    // Mutable state that holds a list of RecipeEntity
    private val _recipes = mutableStateOf<List<RecipeEntity>>(emptyList())

    // Public version of _recipes, exposed as State
    val recipes: State<List<RecipeEntity>> = _recipes

    // Error handling
    private val _error = mutableStateOf("")
    val error: State<String> = _error

    // Function that searches for recipes based on the given query
    // Used in RecipeScreen() function for searching recipes in search bar
    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                Log.d("RecipeViewModel", "Searching for recipes with query: $query")

                // Call the getRecipes() function from RecipeRepository, storing the result (a list of recipes) in 'result;
                val result = repository.getRecipes(query)

                Log.d("RecipeViewModel", "API Response: $result")

                // If recipes were found, update _recipes to make the UI recompose
                if (result.isNotEmpty()) {
                    _recipes.value = result
                    Log.d("RecipeViewModel", "Found ${result.size} recipes.")
                } else {
                    _error.value = "No recipes found for \"$query\"."
                }
            } catch (e: Exception) {
                _error.value = "Something went wrong: ${e.message}"
            }
        }
    }

    private val _selectedRecipe = mutableStateOf<RecipeEntity?>(null)
    val selectedRecipe: State<RecipeEntity?> get() = _selectedRecipe

    fun loadRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            try {
                val recipeDetails = repository.getOrFetchRecipeDetails(recipeId)
                _selectedRecipe.value = recipeDetails

                // After getting details, load ingredients
                loadIngredients(recipeId)
            } catch (e: Exception) {
            }
        }
    }

    // For ingredients
    private val _ingredients = mutableStateOf<List<ExtendedIngredientEntity>>(emptyList())
    val ingredients: State<List<ExtendedIngredientEntity>> = _ingredients

    fun loadIngredients(recipeId: Int) {
        viewModelScope.launch {
            _ingredients.value = repository.getIngredientsForRecipe(recipeId)
        }
    }

    // Grocery list
    private val _groceryList = mutableStateListOf<String>()
    val groceryList: List<String> = _groceryList

    fun addIngredientsToGroceryList(ingredients: List<ExtendedIngredientEntity>) {
        _groceryList.addAll(ingredients.map { it.name })
    }

    // Remove item from grocery list
    fun removeItemFromGroceryList(item: String) {
        _groceryList.remove(item)
    }

}
