package com.example.tracker_domain.model

sealed class MealType(val name: String) {
    data object Dinner : MealType("dinner")
    data object Lunch : MealType("lunch")
    data object BreakFast : MealType("breakfast")

    companion object {
        fun fromString(type: String): MealType {
            return when (type) {
                "breakfast" -> BreakFast
                "lunch" -> Lunch
                "dinner" -> Dinner
                else -> BreakFast
            }
        }
    }
}