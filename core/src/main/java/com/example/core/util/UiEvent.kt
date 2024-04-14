package com.example.core.util

sealed class UiEvent {


    data object Done : UiEvent()

    data object NavigateUp : UiEvent()

    data class ShowSnackBar(val message: UiText) : UiEvent()
}