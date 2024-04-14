package com.example.tracker_presentation.search

import com.example.tracker_domain.model.TrackableFood

data class TrackableFoodState(
    val food: TrackableFood,
    val expanded: Boolean = false,
    val amount: String = "",
)