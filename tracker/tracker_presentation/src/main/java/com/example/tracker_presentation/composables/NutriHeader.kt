package com.example.tracker_presentation.composables

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core_ui.LocalSpacing
import com.example.coreui.CarbColor
import com.example.tracker_presentation.overview.TrackerOverViewState

@Composable
fun NutriHeader(


    state: TrackerOverViewState,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    val animatedCalCount = animateIntAsState(targetValue = state.totalCalories)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(MaterialTheme.colors.primary)
            .padding(
                horizontal = spacing.lg,
                vertical = spacing.xlg
            )
    ) {


        Row {
            UnitDisplay(
                amount = animatedCalCount.value,
                unit = "Kcal",
                amountColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.align(Alignment.Bottom)

            )
        }

        Column {
            Text(
                text = "Your Goal",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary
            )
            UnitDisplay(
                amount = animatedCalCount.value,
                unit = "Kcal",
                amountColor = MaterialTheme.colors.onPrimary,
            )
        }

        Spacer(modifier = Modifier.height(spacing.sm))
        NutriBar(
            carbs = state.totalCarbs,
            protein = state.totalProtein,
            fat = state.totalFat,
            calories = state.totalCalories,
            calGoal = state.caloriesGoal,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        Spacer(modifier = Modifier.height(spacing.lg))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            NutriBarInfo(
                value = state.totalCarbs,
                goal = state.carbGoal,
                name = "Carbs",
                color = CarbColor,
                modifier = Modifier.size(90.dp)

            )
            NutriBarInfo(
                value = state.totalFat,
                goal = state.fatGoal,
                name = "Fat",
                color = CarbColor,
                modifier = Modifier.size(90.dp)

            )
            NutriBarInfo(
                value = state.totalProtein,
                goal = state.proteinGoal,
                name = "Protein",
                color = CarbColor,
                modifier = Modifier.size(90.dp)

            )
        }
    }

}