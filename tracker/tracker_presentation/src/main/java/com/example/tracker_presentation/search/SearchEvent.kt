package com.example.tracker_presentation.search

import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackableFood
import java.time.LocalDate


sealed class SearchEvent {
    data class OnSearchChanged(val q: String) : SearchEvent()


    data object OnSearchPress : SearchEvent()

    data class OnToggleTrackableFood(val food: TrackableFood) : SearchEvent()
    data class OnAmountFoodChanged(
        val food: TrackableFood,
        val amount: String,

        ) : SearchEvent()

    data class OnTrackFoodClick(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate,
    ) : SearchEvent()

    data class OnSearchFocusChanged(val focused: Boolean) : SearchEvent()
}