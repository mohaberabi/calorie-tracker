package com.example.tracker_domain.use_case

import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import java.time.LocalDate

class TrackFoodUseCase(private val trackerRepository: TrackerRepository) {


    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate,

        ) {
        trackerRepository.insertFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbPer100g / 100f) * amount).toInt(),
                protein = ((food.proteinPer100g / 100f) * amount).toInt(),
                fat = ((food.fatPer100g / 100f) * amount).toInt(),
                calories = ((food.carbPer100g / 100f) * amount).toInt(),
                imageUrl = food.imageUrl,
                amount = amount,
                mealType = mealType,
                date = date,
            )
        )
    }
}