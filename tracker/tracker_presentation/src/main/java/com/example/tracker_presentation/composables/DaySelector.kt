package com.example.tracker_presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDate


@Composable
fun DaySelector(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,

    ) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onPrevClick) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )

        }



        Text(
            text = dateParser(date), style = MaterialTheme.typography.h2
        )
        IconButton(onClick = onNextClick) {

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )

        }
    }
}