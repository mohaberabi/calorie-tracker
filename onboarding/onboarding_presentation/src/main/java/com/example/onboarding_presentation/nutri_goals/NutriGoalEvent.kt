package com.example.onboarding_presentation.nutri_goals

sealed class NutriGoalEvent {
    data class OnCarbEnter(val ratio: String) : NutriGoalEvent()
    data class OnFatEnter(val ratio: String) : NutriGoalEvent()
    data class OnProteinEnter(val ratio: String) : NutriGoalEvent()
    data object OnNextClick : NutriGoalEvent()

}