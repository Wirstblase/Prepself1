package store.razvan.prepself.models

data class Recipe(
    val description: String,
    val prepTime: Int,
    val servings: Int,
    val difficulty: String,
    val favourites: Int,
    val id: Int,
    val name: String,
    val nutritionalInfo: NutritionalInfo,
    val thumbnail: String
)