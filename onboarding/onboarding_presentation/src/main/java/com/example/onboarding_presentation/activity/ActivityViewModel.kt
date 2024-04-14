package com.example.onboarding_presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.prefs.Prefs
import com.example.core.navigation.Route
import com.example.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class ActivityViewModel @Inject constructor(
    private val prefs: Prefs
) : ViewModel() {

    var selectedLevel by mutableStateOf<ActivityLevel>(ActivityLevel.Medium)
        private set


    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    fun selectLevel(level: ActivityLevel) {
        selectedLevel = level
    }

    fun onPressNext() {
        viewModelScope.launch {
            prefs.saveActiveLevel(selectedLevel)
            _event.send(UiEvent.Done)
        }
    }
}