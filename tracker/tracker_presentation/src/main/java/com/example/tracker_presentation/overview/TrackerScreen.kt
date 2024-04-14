package com.example.tracker_presentation.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.tracker_presentation.composables.AddButton
import com.example.tracker_presentation.composables.DaySelector
import com.example.tracker_presentation.composables.ExpandableMeal
import com.example.tracker_presentation.composables.NutriHeader
import com.example.tracker_presentation.composables.TrackedFoodItem


@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackerScreen(
    onSearchRequest: (String, Int, Int, Int) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.md)
    ) {

        item {
            NutriHeader(state = state)

            DaySelector(date = state.date,
                onPrevClick = {
                    viewModel.onEvent(TrackerOverViewEvent.OnPreviousDayClicked)
                },
                onNextClick = {
                    viewModel.onEvent(TrackerOverViewEvent.OnNextDayClicked)
                })


        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {
                    viewModel.onEvent(TrackerOverViewEvent.OnToggleMeal(meal))
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.sm)
                    ) {
                        state.trackedFood.forEach { food ->
                            TrackedFoodItem(
                                trackedFood = food,
                                onDeleteClick = {
                                    viewModel.onEvent(
                                        TrackerOverViewEvent
                                            .OnDeleteFoodClicked(food)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(spacing.md))
                        }
                        AddButton(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.fillMaxWidth(),
                            text = "Add ${meal.name.asString(context)}",

                            onClick = {
                                onSearchRequest(
                                    meal.name.asString(context),
                                    state.date.dayOfMonth,
                                    state.date.monthValue,
                                    state.date.year,
                                )
                            },

                            )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}


