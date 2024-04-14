package com.example.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.R
import com.example.core.domain.prefs.Prefs
import com.example.core.navigation.Route
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val prefs: Prefs,
) : ViewModel() {

    var height by mutableStateOf("80.0")
        private set

    private val _event = Channel<UiEvent>()
    val event = _event.consumeAsFlow()

    fun weightChanged(weight: String) {
        if (weight.length <= 5) {
            this.height
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNo = height.toFloatOrNull() ?: kotlin.run {
                _event.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringRes(R.string.empty_string_err),
                    ),
                )
                return@launch
            }
            prefs.saveWeight(ageNo.roundToInt())
            _event.send(UiEvent.Done)
        }
    }
}