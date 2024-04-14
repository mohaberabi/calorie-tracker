package com.example.core.domain.model

sealed class GoalType(val name: String) {

    data object KeepWeight : GoalType("keep")
    data object GainWeight : GoalType("gain")

    data object LoseWeight : GoalType("lose")


    companion object {
        fun fromString(name: String): GoalType {
            return if (name == "keep") {
                GoalType.KeepWeight
            } else if (name == "gain") {
                GoalType.GainWeight

            } else {
                GoalType.LoseWeight
            }
        }
    }
}