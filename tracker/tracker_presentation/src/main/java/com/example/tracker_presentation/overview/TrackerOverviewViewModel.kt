package com.example.tracker_presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.prefs.Prefs
import com.example.core.util.UiEvent
import com.example.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    private val prefs: Prefs,
    private val useCases: TrackerUseCases,

    ) : ViewModel() {
    var state by mutableStateOf(TrackerOverViewState())
        private set


    private var getFoodBydateJob: Job? = null
    private val _uiEvent = Channel<UiEvent>()

    val event = _uiEvent.receiveAsFlow()

    init {
        refreshFoods()
        prefs.saveOnBoarding()
    }

    fun onEvent(event: TrackerOverViewEvent) {
        when (event) {
//            is TrackerOverViewEvent.OnAddFoodClicked -> {
//                viewModelScope.launch {
//                    _uiEvent.send(
//                        UiEvent.Done
//                        //   com.example.core.navigation.Route.SEARCH +
//                        //                                    "/${event.meal.mealType.name}" +
//                        //                                    "/${state.date.dayOfMonth}" +
//                        //                                    "/${state.date.monthValue}" +
//                        //                                    "/${state.date.year}"
//                    )
//                }
//            }

            is TrackerOverViewEvent.OnDeleteFoodClicked -> {
                viewModelScope.launch {
                    useCases.deleteFoodUseCase(event.trackedFood)
                    refreshFoods()
                }
            }

            is TrackerOverViewEvent.OnToggleMeal -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }

            is TrackerOverViewEvent.OnNextDayClicked -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }

            is TrackerOverViewEvent.OnPreviousDayClicked -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }

        }
    }

    private fun refreshFoods() {
        getFoodBydateJob?.cancel()
        getFoodBydateJob = useCases.getFoodByDateUseCase(state.date).onEach { foods ->
            val nutriResult = useCases.calcMealNutriUSeCase(foods)
            state = state.copy(
                totalCarbs = nutriResult.totalCarb,
                totalProtein = nutriResult.totalProtein,
                totalFat = nutriResult.totalFat,
                totalCalories = nutriResult.totalCal,
                carbGoal = nutriResult.carbGoal,
                fatGoal = nutriResult.fatGoal,
                proteinGoal = nutriResult.protienGoal,
                meals = state.meals.map {
                    val nutriMeal = nutriResult.mealNutri[it.mealType]
                        ?: return@map it.copy(carbs = 0, protein = 0, fat = 0, cal = 0)
                    it.copy(
                        carbs = nutriMeal.carbs,
                        cal = nutriMeal.cal,
                        protein = nutriMeal.protein
                    )
                }
            )
        }.launchIn(viewModelScope)
    }
}