package com.example.data.prefs

import android.content.SharedPreferences
import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.prefs.Prefs

class DefaultPrefs(
    private val prefs: SharedPreferences
) : Prefs {
    override fun saveGender(gender: Gender) {
        prefs.edit().putString(Prefs.genderKey, gender.name).apply()
    }

    override fun saveOnBoarding() {
        prefs.edit().putBoolean(Prefs.onBoardingKey, true).apply()
    }

    override fun loadOnBoarding(): Boolean {
        return prefs.getBoolean(Prefs.onBoardingKey, false)
    }

    override fun sveAge(age: Int) {
        prefs.edit().putInt(Prefs.ageKey, age).apply()
    }

    override fun saveHeight(height: Int) {
        prefs.edit().putInt(Prefs.heightKey, height).apply()
    }

    override fun saveWeight(weight: Int) {
        prefs.edit().putInt(Prefs.heightKey, weight).apply()
    }

    override fun saveGoal(goal: GoalType) {
        prefs.edit().putString(Prefs.goalKey, goal.name).apply()
    }

    override fun saveActiveLevel(level: ActivityLevel) {
        prefs.edit().putString(Prefs.levelKey, level.name).apply()
    }

    override fun saveCarb(carb: Int) {
        prefs.edit().putInt(Prefs.carbKey, carb).apply()
    }

    override fun saveFat(fat: Int) {
        prefs.edit().putInt(Prefs.fatKey, fat).apply()
    }

    override fun saveProtein(protein: Int) {
        prefs.edit().putInt(Prefs.proteinKey, protein).apply()
    }

    override fun loadUserInfo(): UserInfo {

        val age = prefs.getInt(Prefs.ageKey, -1)
        val weight = prefs.getInt(Prefs.weightKey, -1)
        val height = prefs.getInt(Prefs.ageKey, -1)
        val gender = prefs.getString(Prefs.genderKey, null)
        val level = prefs.getString(Prefs.levelKey, null)
        val goal = prefs.getString(Prefs.goalKey, null)
        val carb = prefs.getInt(Prefs.ageKey, -1)
        val fat = prefs.getInt(Prefs.ageKey, -1)
        val protein = prefs.getInt(Prefs.ageKey, -1)

        return UserInfo(
            height = height,
            weight = weight,
            goal = GoalType.fromString(goal ?: "keep"),
            protein = protein,
            fat = fat,
            carb = carb,
            level = ActivityLevel.fromString(level ?: "medium"),
            age = age,
            gender = Gender.fromString(gender ?: "male"),
        )
    }
}