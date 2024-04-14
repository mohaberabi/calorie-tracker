package com.example.tracker_data.mapper

import com.example.tracker_data.local.entity.TrackedFoodEntity
import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import java.time.LocalDate


fun TrackedFoodEntity.toTrackedFood(): TrackedFood {

    return TrackedFood(
        name = name,
        protein = protein,
        fat = fat,
        carbs = carbs,
        imageUrl = imageUrl,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id,
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {

    return TrackedFoodEntity(
        name = name,
        protein = protein,
        fat = fat,
        carbs = carbs,
        imageUrl = imageUrl,
        type = mealType.name,
        amount = amount,
        year = date.year,
        month = date.month.value,
        dayOfMonth = date.month.value,
        calories = calories,
        id = id,
    )
}