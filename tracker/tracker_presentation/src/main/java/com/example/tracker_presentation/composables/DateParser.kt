package com.example.tracker_presentation.composables

import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun dateParser(date: LocalDate): String {

    val today = LocalDate.now()

    return when (date) {
        today -> "Today"
        today.minusDays(1) -> "Yesterday"
        today.plusDays(1) -> "Tomorrow"

        else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)
    }
}