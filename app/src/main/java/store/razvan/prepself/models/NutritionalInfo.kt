package store.razvan.prepself.models

data class NutritionalInfo(
    val calories: Int,
    val carbohydratesInGrams: Int,
    val fatsInGrams: Int,
    val id: Long?,
    val proteinsInGrams: Int
)