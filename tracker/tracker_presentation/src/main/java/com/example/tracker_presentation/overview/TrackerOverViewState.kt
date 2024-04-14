package com.example.tracker_presentation.overview

import com.example.tracker_domain.model.TrackedFood
import java.time.LocalDate

data class TrackerOverViewState(


    val totalCarbs: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCalories: Int = 0,
    val carbGoal: Int = 0,
    val proteinGoal: Int = 0,
    val fatGoal: Int = 0,
    val caloriesGoal: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val meals: List<Meal> = com.example.tracker_presentation.overview.defaultMeals,
    val trackedFood: List<TrackedFood> = emptyList<TrackedFood>()


)
