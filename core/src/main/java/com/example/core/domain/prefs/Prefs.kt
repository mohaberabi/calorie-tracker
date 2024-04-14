package com.example.core.domain.prefs

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo

interface Prefs {


    fun saveGender(gender: Gender)
    fun sveAge(age: Int)
    fun saveHeight(height: Int)
    fun saveWeight(weight: Int)
    fun saveGoal(goal: GoalType)
    fun saveActiveLevel(level: ActivityLevel)
    fun saveCarb(carb: Int)
    fun saveFat(fat: Int)
    fun saveProtein(protein: Int)
    fun saveOnBoarding()
    fun loadOnBoarding(): Boolean

    fun loadUserInfo(): UserInfo

    companion object {
        const val weightKey = "weightKey"
        const val heightKey = "heightKey"
        const val ageKey = "ageKey"
        const val goalKey = "goalKey"
        const val levelKey = "levelKey"
        const val genderKey = "genderKey"
        const val proteinKey = "proteinKey"
        const val carbKey = "carbKey"
        const val fatKey = "fatKey"
        const val onBoardingKey = "onBoardingKey"

    }

}