package com.example.onboarding_presentation.gender

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.R
import com.example.core.domain.model.Gender
import com.example.core.util.UiEvent
import com.example.onboarding_presentation.base.ChoiceScreen


@Composable
fun GenderScreen(
    onNextClick: () -> Unit,
    viewModel: GenderViewModel = hiltViewModel(),

    ) {
    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.Done -> onNextClick()
                else -> Unit
            }
        }
    }


    ChoiceScreen<Gender>(
        title = stringResource(id = R.string.select_gender),
        onAction = {
            onNextClick()
        }, value = viewModel.selectedGender,
        choices = listOf(
            Pair(
                Gender.Male, stringResource(id = R.string.male)
            ),
            Pair(
                Gender.Female, stringResource(id = R.string.female)
            )
        ),
        onValChanged = {
            viewModel.selectGender(it)
        }
    )

}