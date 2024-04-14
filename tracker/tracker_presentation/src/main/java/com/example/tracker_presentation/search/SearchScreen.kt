package com.example.tracker_presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.tracker_domain.model.MealType
import com.example.tracker_presentation.composables.SearchTextField
import com.example.tracker_presentation.composables.TrackableFoodItem
import java.time.LocalDate

@OptIn(ExperimentalCoilApi::class)
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
//    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = context) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
//                    keyboardController?.hide()
                }

                is UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.md)
    ) {
        Text(
            text = "Add meal${mealName}",
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.md))
        SearchTextField(
            text = state.query,
            onValueChange = {
                viewModel.onEvent(SearchEvent.OnSearchChanged(it))
            },
            onSearch = {
                viewModel.onEvent(SearchEvent.OnSearchPress)
            },
            onFocusChanged = {
                viewModel.onEvent(SearchEvent.OnSearchFocusChanged(it.isFocused))
            }
        )

        LazyColumn {
            items(state.foods) { food ->
                TrackableFoodItem(
                    trackableFoodUiState = food,
                    onClick = {
                        viewModel.onEvent(
                            SearchEvent.OnToggleTrackableFood(
                                food = food.food,

                                )
                        )
                    },
                    onAmountChange = {
                        viewModel.onEvent(SearchEvent.OnSearchChanged(it))
                    },
                    onTrack = {
                        viewModel.onEvent(
                            SearchEvent.OnTrackFoodClick(
                                food = food.food, mealType = MealType.fromString(mealName),
                                date = LocalDate.of(year, month, dayOfMonth)

                            )
                        )
                    }, modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.foods.isEmpty() -> Text(text = "NO RESULTS LOSER")

        }
    }
}