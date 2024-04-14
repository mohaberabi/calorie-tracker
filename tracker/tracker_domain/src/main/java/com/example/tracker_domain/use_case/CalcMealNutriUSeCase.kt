package com.example.tracker_domain.use_case

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.prefs.Prefs
import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalcMealNutriUSeCase(
    private val prefs: Prefs
) {


    operator fun invoke(trackedFood: List<TrackedFood>): CalcResult {
        val allNutri = trackedFood.groupBy {
            it.mealType
        }.mapValues { entry ->
            val type = entry.key
            val food = entry.value
            MealNutri(
                carbs = food.sumOf { it.carbs },
                protein = food.sumOf { it.protein },
                fat = food.sumOf { it.fat },
                cal = food.sumOf { it.calories },
                mealType = type,
            )
        }
        val totalCarbs = allNutri.values.sumOf { it.carbs }
        val totalFat = allNutri.values.sumOf { it.fat }
        val totalProtein = allNutri.values.sumOf { it.protein }
        val totalCal = allNutri.values.sumOf { it.cal }


        val userInfo = prefs.loadUserInfo()

        val calGoal = dailyCaloryRequirement(userInfo)
        val carbsGoal = (calGoal * userInfo.carb / 4f).roundToInt()
        val fatGoal = (calGoal * userInfo.fat / 9f).roundToInt()
        val proteinGoal = (calGoal * userInfo.protein / 4f).roundToInt()
        return CalcResult(
            calGoal = carbsGoal,
            fatGoal = fatGoal,
            protienGoal = proteinGoal,
            totalCal = totalCal,
            totalFat = totalFat,
            totalCarb = totalCarbs,
            totalProtein = totalProtein,
            mealNutri = allNutri,
            carbGoal = carbsGoal,
        )
    }

    data class MealNutri(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val cal: Int,
        val mealType: MealType,
    )


    data class CalcResult(
        val carbGoal: Int,
        val protienGoal: Int,
        val fatGoal: Int,
        val calGoal: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCarb: Int,
        val totalCal: Int,
        val mealNutri: Map<MealType, MealNutri>
    )

    private fun bmr(userInfo: UserInfo): Int {
        return when (userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }

            is Gender.Female -> {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }

    private fun dailyCaloryRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.level) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when (userInfo.goal) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }

}