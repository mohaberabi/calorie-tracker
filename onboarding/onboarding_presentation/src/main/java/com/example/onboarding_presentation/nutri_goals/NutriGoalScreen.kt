package com.example.onboarding_presentation.nutri_goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.onboarding_presentation.composes.ActionButton
import com.example.onboarding_presentation.composes.UnitTextField


@Composable
fun NutriGoalScreen(
    scaffoldState: ScaffoldState,
    onNextClick: () -> Unit,
    viewModel: NutriGoalViewModel = hiltViewModel()
) {


    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val state = viewModel.state
    LaunchedEffect(key1 = true) {
        viewModel.event.collect() { event ->
            when (event) {
                is UiEvent.Done -> onNextClick()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                }

                else -> Unit
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {


        UnitTextField(
            value = state.proteinRatio,
            title = "Protein",
            onChanged = {
                viewModel.onEvent(NutriGoalEvent.OnProteinEnter(it))
            })
        UnitTextField(
            title = "Carb",

            value = state.carbsRatio,
            onChanged = {
                viewModel.onEvent(NutriGoalEvent.OnCarbEnter(it))
            })
        UnitTextField(
            title = "Fat",
            value = state.fatRatio,

            onChanged = {
                viewModel.onEvent(NutriGoalEvent.OnFatEnter(it))
            })


        ActionButton(text = stringResource(id = com.example.core.R.string.next),
            isEnabled = true,
            onClick = {

                onNextClick()

            })
    }


}


@Preview

@Composable
fun PreviewNutriGoalScreen() {

    NutriGoalScreen(
        scaffoldState = rememberScaffoldState(), onNextClick = {},
    )
}