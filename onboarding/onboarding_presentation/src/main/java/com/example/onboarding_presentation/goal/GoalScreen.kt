package com.example.onboarding_presentation.goal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.R
import com.example.core.domain.model.GoalType
import com.example.core.util.UiEvent
import com.example.onboarding_presentation.base.ChoiceScreen


@Composable
fun GoalScreen(

    viewModel: GoalViewModel = hiltViewModel(),

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
        Pair(GoalType.KeepWeight, stringResource(id = R.string.keep)),
        Pair(GoalType.LoseWeight, stringResource(id = R.string.lose)),
        Pair(GoalType.GainWeight, stringResource(id = R.string.keep)),
    )
    ChoiceScreen(
        onAction = { onNextClick() },
        title = stringResource(id = R.string.what_goal),
        value = viewModel.selectedGoal,
        choices = choices,
        onValChanged = viewModel::selectGoal
    )

}