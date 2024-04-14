package com.example.tracker_domain.model

data class TrackableFood(
    val name: String,
    val imageUrl: String?,
    val calPer100g: Int,
    val fatPer100g: Int,
    val proteinPer100g: Int,
    val carbPer100g: Int,
)

