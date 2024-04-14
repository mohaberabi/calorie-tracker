package com.example.onboarding_presentation.age

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.R
import com.example.core.util.UiEvent
import com.example.onboarding_presentation.base.ChoiceScreen

@Composable

fun AgeScreen(
    viewModel: AgeViewModel = hiltViewModel(),
    onNextClick: () -> Unit = {},
    scaffoldState: ScaffoldState,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.Done -> onNextClick()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }



    ChoiceScreen(
        value = viewModel.age,
        title = stringResource(id = R.string.wahts_age),
        onValChanged = viewModel::ageChanged,
        onAction = { onNextClick() }
    )
}