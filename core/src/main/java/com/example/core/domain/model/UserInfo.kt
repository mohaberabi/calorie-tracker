package com.example.core.domain.model

data class UserInfo(
    val gender: Gender,
    val age: Int,
    val weight: Int,
    val height: Int,
    val level: ActivityLevel,
    val goal: GoalType,
    val fat: Int,
    val protein: Int,
    val carb: Int

)
