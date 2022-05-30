package store.razvan.prepself.models

data class RecipeResponse(
    val body: List<Recipe>,
    val errorMessage: String?,
    val success: Boolean
)