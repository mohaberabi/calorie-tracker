package com.example.tracker_presentation.overview

import com.example.tracker_domain.model.TrackedFood

sealed class TrackerOverViewEvent {


    data object OnNextDayClicked : TrackerOverViewEvent()
    data object OnPreviousDayClicked : TrackerOverViewEvent()

    data class OnToggleMeal(val meal: Meal) : TrackerOverViewEvent()
    data class OnDeleteFoodClicked(val trackedFood: TrackedFood) : TrackerOverViewEvent()
}