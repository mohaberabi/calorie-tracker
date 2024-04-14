package com.example.tracker_presentation.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.example.coreui.CarbColor
import com.example.coreui.FatColor
import com.example.coreui.ProteinColor


@Composable
fun NutriBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calGoal: Int,
    modifier: Modifier = Modifier
) {

    val background = MaterialTheme.colors.background
    val calExceedColor = MaterialTheme.colors.error
    val carbWidthRatio = remember {
        Animatable(0f)
    }

    val proteinWidthRatio = remember {
        Animatable(0f)
    }
    val fatWidthRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(
        carbWidthRatio
    ) {
        carbWidthRatio.animateTo(((carbs * 4f) / calories))
    }
    LaunchedEffect(
        proteinWidthRatio
    ) {
        proteinWidthRatio.animateTo(((carbs * 4f) / calories))
    }
    LaunchedEffect(
        fat
    ) {
        fatWidthRatio.animateTo(((carbs * 9f) / calories))
    }

    Canvas(modifier = modifier) {
        if (calories <= calGoal) {
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinWidth = proteinWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width

            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = CornerRadius(100f)

            )
            drawRoundRect(
                color = FatColor,
                size = Size(width = carbsWidth + proteinWidth + fatWidth, height = size.height),
                cornerRadius = CornerRadius(100f)

            )
            drawRoundRect(

                color = ProteinColor,
                size = Size(width = carbsWidth + proteinWidth, height = size.height),
                cornerRadius = CornerRadius(100f)

            )

            drawRoundRect(
                color = CarbColor,
                size = Size(width = carbsWidth, height = size.height),
                cornerRadius = CornerRadius(100f)

            )


        } else {
            drawRoundRect(
                color = calExceedColor,
                size = size,
                cornerRadius = CornerRadius(100f)

            )
        }
    }
}