package com.example.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.FilterDigits
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.example.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackUseCase: TrackerUseCases,
    private val filterDigits: FilterDigits,

    ) : ViewModel() {


    var state by mutableStateOf(SearchState())
        private set
    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()
    fun onEvent(event: SearchEvent) {
        when (event) {

            is SearchEvent.OnSearchChanged -> {
                state = state.copy(query = event.q)

            }

            is SearchEvent.OnSearchPress -> {

                viewModelScope.launch {
                    startSearching()
                }
            }

            is SearchEvent.OnSearchFocusChanged -> {

                state = state.copy(
                    isHintVisible = !event.focused && state.query.isBlank()
                )
            }

            is SearchEvent.OnAmountFoodChanged -> {


                state = state.copy(
                    foods = state.foods.map {
                        if (it.food == event.food) {
                            it.copy(amount = filterDigits(event.amount))
                        } else {
                            it
                        }
                    }
                )

            }

            is SearchEvent.OnTrackFoodClick -> {

                trackFood(event)
            }

            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    foods = state.foods.map {
                        if (it.food == event.food) {
                            it.copy(expanded = !it.expanded)
                        } else {
                            it
                        }
                    }
                )
            }

        }

    }


    private fun startSearching() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                foods = emptyList(),
            )
            trackUseCase.searchFood(state.query, 1, 10).onSuccess { foods ->


                state = state.copy(
                    foods = foods.map {
                        TrackableFoodState(it)
                    },
                    isSearching = false
                )
            }.onFailure {
                state = state.copy(isSearching = false)

                _event.send(
                    UiEvent.ShowSnackBar(
                        UiText.DynamicString("ERRORRRR ${it}")
                    )
                )
            }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val curr = state.foods.find {
                it.food == event.food
            }

            trackUseCase.trackFood(
                food = curr?.food ?: return@launch,
                amount = curr.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _event.send(UiEvent.NavigateUp)

        }
    }

}