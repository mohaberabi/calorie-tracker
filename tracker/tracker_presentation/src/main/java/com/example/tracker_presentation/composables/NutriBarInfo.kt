package com.example.tracker_presentation.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent


@Composable
fun NutriBarInfo(
    value: Int,
    goal: Int,
    name: String,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp,
) {


    val background = MaterialTheme.colors.background
    val goalExceded = MaterialTheme.colors.error
    val angleRatio = remember {
        Animatable(0f)
    }
    val textColor = if (value <= goal) {
        MaterialTheme.colors.primary

    } else goalExceded
    LaunchedEffect(key1 = value) {
        angleRatio.animateTo(
            targetValue = if (goal > 0) {
                value / goal.toFloat()
            } else 0f,
            animationSpec = tween(
                durationMillis = 300
            )
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxWidth(1f)) {
            drawArc(
                color = if (value <= goal) background else goalExceded,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size, style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
            if (value <= goal) {
                drawArc(
                    color = color, startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    size = size, style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UnitDisplay(
                amount = value,
                unit = "Gm",
                amountColor = textColor,
                unitColor = textColor,
            )


            Text(
                text = name,
                color = textColor,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Light
            )

        }
    }

}