package com.example.tracker_data.mapper

import com.example.tracker_data.remote.dto.Product
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt


fun Product.toTrackableFood(): TrackableFood? {
    return TrackableFood(
        name = productName ?: return null,
        carbPer100g = nutriments.carbohydrates100g.roundToInt(),
        fatPer100g = nutriments.fat100g.roundToInt(),
        proteinPer100g = nutriments.proteins100g.roundToInt(),
        imageUrl = imageFrontThumbUrl, calPer100g = nutriments.energyKcal100g.roundToInt()

    )
}