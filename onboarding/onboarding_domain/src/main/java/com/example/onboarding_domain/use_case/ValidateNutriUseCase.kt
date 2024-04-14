package com.example.onboarding_domain.use_case

import com.example.core.R
import com.example.core.util.UiText
import javax.inject.Inject

class ValidateNutriUseCase @Inject constructor() {


    operator fun invoke(
        carb: String,
        protein: String,
        fat: String,
    ): NutriResult {


        val carbRatio = carb.toIntOrNull()

        val proteinRatio = protein.toIntOrNull()
        val fatRatio = fat.toIntOrNull()

        if (carbRatio == null || fatRatio == null || proteinRatio == null) {
            return NutriResult.Error(UiText.StringRes(R.string.empty_string_err))
        }

        val total = carbRatio + fatRatio + proteinRatio
        if (total != 100) {
            return NutriResult.Error(UiText.StringRes(R.string.nutri_not_100))

        }

        return NutriResult.Done(
            carbRatio / 100f,
            fatRatio / 100f,
            proteinRatio / 100f
        )

    }


}

sealed class NutriResult {
    data class Done(
        val carb: Float,
        val fat: Float,
        val protein: Float,
    ) : NutriResult()

    data class Error(
        val message: UiText
    ) : NutriResult()

}