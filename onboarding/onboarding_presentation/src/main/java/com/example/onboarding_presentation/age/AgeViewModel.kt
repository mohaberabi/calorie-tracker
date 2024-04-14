package com.example.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.R
import com.example.core.domain.prefs.Prefs
import com.example.core.domain.use_case.FilterDigits
import com.example.core.navigation.Route
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AgeViewModel @Inject constructor(
    private val prefs: Prefs,
    private val filterDigits: FilterDigits,
) : ViewModel() {

    var age by mutableStateOf("20")
        private set

    private val _event = Channel<UiEvent>()
    val event = _event.consumeAsFlow()

    fun ageChanged(age: String) {
        if (age.length <= 3) {
            this.age = filterDigits.invoke(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNo = age.toIntOrNull() ?: kotlin.run {
                _event.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringRes(R.string.empty_string_err),
                    ),
                )
                return@launch
            }
            prefs.sveAge(ageNo)
            _event.send(UiEvent.Done)
        }
    }
}