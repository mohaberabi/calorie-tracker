package com.example.onboarding_presentation.nutri_goals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.prefs.Prefs
import com.example.core.domain.use_case.FilterDigits
import com.example.core.navigation.Route
import com.example.core.util.UiEvent
import com.example.onboarding_domain.use_case.NutriResult
import com.example.onboarding_domain.use_case.ValidateNutriUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class NutriGoalViewModel @Inject constructor(
    private val prefs: Prefs,
    private val filterDigits: FilterDigits,

    private val validateNutriUseCase: ValidateNutriUseCase,

    ) : ViewModel() {

    var state by mutableStateOf(NutriGoalState())
        private set


    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()


    fun onEvent(event: NutriGoalEvent) {

        when (event) {
            is NutriGoalEvent.OnCarbEnter -> {
                state = state.copy(
                    carbsRatio = filterDigits(event.ratio)
                )
            }

            is NutriGoalEvent.OnFatEnter -> {
                state = state.copy(
                    fatRatio = filterDigits(event.ratio)
                )
            }

            is NutriGoalEvent.OnProteinEnter -> {
                state = state.copy(
                    proteinRatio = filterDigits(event.ratio)
                )
            }

            is NutriGoalEvent.OnNextClick -> {
                val result = validateNutriUseCase(
                    carb = state.carbsRatio,
                    protein = state.proteinRatio,
                    fat = state.fatRatio
                )
                when (result) {
                    is NutriResult.Error -> {

                        viewModelScope.launch {
                            _event.send(UiEvent.ShowSnackBar(result.message))
                        }
                    }


                    is NutriResult.Done -> {
                        prefs.saveFat(result.fat.roundToInt())
                        prefs.saveCarb(result.carb.roundToInt())
                        prefs.saveProtein(result.protein.roundToInt())
                        viewModelScope.launch {
                            _event.send(UiEvent.Done)
                        }
                    }
                }

            }
        }
    }

}