package com.example.onboarding_presentation.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Gender
import com.example.core.domain.prefs.Prefs
import com.example.core.navigation.Route
import com.example.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val prefs: Prefs,
) : ViewModel() {

    var selectedGender by mutableStateOf<Gender>(Gender.Male)
        private set


    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    fun selectGender(gender: Gender) {
        selectedGender = gender
    }

    fun onPressNext() {
        viewModelScope.launch {
            prefs.saveGender(selectedGender)
            _event.send(UiEvent.Done)
        }
    }
}