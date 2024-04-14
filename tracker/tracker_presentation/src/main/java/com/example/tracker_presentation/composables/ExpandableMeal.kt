package com.example.tracker_presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberImagePainter

import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.sp
import com.example.core_ui.LocalSpacing
import com.example.tracker_presentation.overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleClick() }
                .padding(spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context)
            )
            Spacer(modifier = Modifier.width(spacing.md))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = meal.name.asString(context),
                        style = MaterialTheme.typography.h3
                    )
                    Icon(
                        imageVector = if (meal.isExpanded) {
                            Icons.Default.KeyboardArrowUp
                        } else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (meal.isExpanded) {
                            "Colapse"
                        } else "Extend"
                    )
                }
                Spacer(modifier = Modifier.height(spacing.sm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.cal,
                        unit = "Kcal",
                    )
                    Row {
                        NutrientInfo(
                            name = "Carbs",
                            amount = meal.carbs,
                            unit = "Gm"
                        )
                        Spacer(modifier = Modifier.width(spacing.sm))
                        NutrientInfo(
                            name = "Protein",
                            amount = meal.protein,
                            unit = "Gm"
                        )
                        Spacer(modifier = Modifier.width(spacing.sm))
                        NutrientInfo(
                            name = "Fat",
                            amount = meal.fat,
                            unit = "Gm",
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.md))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}