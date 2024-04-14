package com.example.onboarding_presentation.activity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.R
import com.example.core.domain.model.ActivityLevel
import com.example.core.util.UiEvent
import com.example.onboarding_presentation.base.ChoiceScreen


@Composable
fun ActivityScreen(

    viewModel: ActivityViewModel = hiltViewModel(),

    onNextClick: () -> Unit,
) {


    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.Done -> onNextClick()
                else -> Unit
            }
        }
    }
    val choices = listOf(
        Pair(ActivityLevel.Low, stringResource(id = R.string.low)),
        Pair(ActivityLevel.Medium, stringResource(id = R.string.medium)),
        Pair(ActivityLevel.High, stringResource(id = R.string.high)),
    )
    ChoiceScreen(
        onAction = { onNextClick() },
        title = stringResource(id = R.string.choos_level),
        value = viewModel.selectedLevel,
        choices = choices,

        onValChanged = viewModel::selectLevel

    )

}