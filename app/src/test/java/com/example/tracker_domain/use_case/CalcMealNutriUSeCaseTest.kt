package com.example.tracker_domain.use_case

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.prefs.Prefs
import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import com.google.common.truth.Truth.assertThat

class CalcMealNutriUSeCaseTest {


    private lateinit var calcUseCase: CalcMealNutriUSeCase


    @Before

    fun setup() {

        val prefs = mockk<Prefs>(relaxed = true)

        every {
            prefs.loadUserInfo()
        } returns UserInfo(
            gender = Gender.Male,
            age = 20, weight = 20,
            height = 100,
            level = ActivityLevel.High,
            goal = GoalType.LoseWeight,
            carb = 20,
            protein = 20,
            fat = 20,
        )
        calcUseCase = CalcMealNutriUSeCase(prefs)

    }

    @Test
    fun `cal for breakfast calculated done `() {

        val tracked = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = 20,
                fat = 29,
                protein = 20,
                calories = 20,
                mealType = MealType.fromString(
                    listOf<String>(
                        "dinner",
                        "launch",
                        "breakfast"
                    ).random()
                ),
                date = LocalDate.now(),
                amount = 1,
                imageUrl = ""
            )
        }
        val res = calcUseCase(tracked)
        val breakfast = res.mealNutri.values.filter {
            it.mealType is MealType.BreakFast
        }.sumOf { it.cal }


        val expected = tracked.filter {
            it.mealType is MealType.BreakFast
        }.sumOf { it.calories }

        assertThat(breakfast).isEqualTo(expected)
    }

    @Test
    fun `carb for breakfast calculated done `() {

        val tracked = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = 20,
                fat = 29,
                protein = 20,
                calories = 20,
                mealType = MealType.fromString(
                    listOf<String>(
                        "dinner",
                        "launch",
                        "breakfast"
                    ).random()
                ),
                date = LocalDate.now(),
                amount = 1,
                imageUrl = ""
            )
        }
        val res = calcUseCase(tracked)
        val breakfast = res.mealNutri.values.filter {
            it.mealType is MealType.BreakFast
        }.sumOf { it.carbs }


        val expected = tracked.filter {
            it.mealType is MealType.BreakFast
        }.sumOf { it.carbs }

        assertThat(breakfast).isEqualTo(expected)
    }

    @Test
    fun `fat for breakfast calculated done `() {

        val tracked = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = 20,
                fat = 29,
                protein = 20,
                calories = 20,
                mealType = MealType.fromString(
                    listOf<String>(
                        "dinner",
                        "launch",
                        "breakfast"
                    ).random()
                ),
                date = LocalDate.now(),
                amount = 1,
                imageUrl = ""
            )
        }
        val res = calcUseCase(tracked)
        val breakfast = res.mealNutri.values.filter {
            it.mealType is MealType.BreakFast
        }.sumOf { it.fat }


        val expected = tracked.filter {
            it.mealType is MealType.BreakFast
        }.sumOf { it.fat }

        assertThat(breakfast).isEqualTo(expected)
    }

    @Test
    fun `protein for breakfast calculated done `() {

        val tracked = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = 20,
                fat = 29,
                protein = 20,
                calories = 20,
                mealType = MealType.fromString(
                    listOf<String>(
                        "dinner",
                        "launch",
                        "breakfast"
                    ).random()
                ),
                date = LocalDate.now(),
                amount = 1,
                imageUrl = ""
            )
        }
        val res = calcUseCase(tracked)
        val breakfast = res.mealNutri.values.filter {
            it.mealType is MealType.BreakFast
        }.sumOf { it.protein }


        val expected = tracked.filter {
            it.mealType is MealType.BreakFast
        }.sumOf { it.protein }

        assertThat(breakfast).isEqualTo(expected)
    }
}