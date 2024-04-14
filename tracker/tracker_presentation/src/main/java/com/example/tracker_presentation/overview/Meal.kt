package com.example.tracker_presentation.overview

import androidx.annotation.DrawableRes
import com.example.core.R
import com.example.core.util.UiText
import com.example.tracker_domain.model.MealType

data class Meal(
    val name: UiText,
    @DrawableRes val drawableRes: Int,
    val protein: Int = 0,
    val cal: Int = 0,
    val fat: Int = 0,
    val carbs: Int = 0,
    val isExpanded: Boolean = false,
    val mealType: MealType
)


val defaultMeals = listOf<Meal>(
    Meal(
        UiText.StringRes(R.string.breakfast),

        drawableRes = R.drawable.ic_breakfast,
        mealType = MealType.BreakFast,
    ),
    Meal(
        UiText.StringRes(R.string.lunch),

        drawableRes = R.drawable.ic_lunch,
        mealType = MealType.Lunch,
    ),
    Meal(
        UiText.StringRes(R.string.dinner),

        drawableRes = R.drawable.ic_dinner,
        mealType = MealType.Dinner,
    ),

    )