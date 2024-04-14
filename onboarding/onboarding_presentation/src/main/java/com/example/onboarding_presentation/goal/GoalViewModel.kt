package com.example.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.GoalType
import com.example.core.domain.prefs.Prefs
import com.example.core.navigation.Route
import com.example.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val prefs: Prefs) : ViewModel() {
    var selectedGoal by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set


    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    fun selectGoal(goal: GoalType) {
        selectedGoal = goal
    }

    fun onPressNext() {
        viewModelScope.launch {
            prefs.saveGoal(selectedGoal)
            _event.send(UiEvent.Done)
        }
    }
}