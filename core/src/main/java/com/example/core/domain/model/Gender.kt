package com.example.core.domain.model

sealed class Gender(val name: String) {

    data object Female : Gender("Female")

    data object Male : Gender("Male")


    companion object {


        fun fromString(value: String): Gender {

            return if (value == "Male") {
                Gender.Male
            } else {
                Gender.Female
            }
        }
    }
}