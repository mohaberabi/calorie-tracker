package com.example.onboarding_presentation.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.core.R
import com.example.core.domain.model.Gender
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.onboarding_presentation.composes.ActionButton
import com.example.onboarding_presentation.composes.SelectableButton
import com.example.onboarding_presentation.composes.UnitTextField
import kotlinx.coroutines.flow.Flow


@Composable
fun ChoiceScreen(
    onValChanged: (String) -> Unit = {},
    value: String,
    title: String,
    onAction: () -> Unit
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.lg)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.md))

            UnitTextField(value = value,
                modifier = Modifier, onChanged = {
                    onValChanged.invoke(it)
                }
            )

            ActionButton(
                text = stringResource(id = R.string.next),
                isEnabled = true,
                onClick = { onAction.invoke() },
                modifier = Modifier,
            )
        }
    }

}

@Composable
fun <T> ChoiceScreen(


    onAction: () -> Unit,
    title: String,
    onValChanged: (T) -> Unit = {},
    value: T,
    choices: List<Pair<T, String>>,
) {


    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.lg)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.md))


            Row {

                choices.map { pair ->
                    SelectableButton(
                        modifier = Modifier.padding(spacing.sm),
                        text = pair.second,
                        selected = pair.first == value,
                        color = MaterialTheme.colors.primaryVariant,
                        selectedColor = Color.White,
                        onPress = { onValChanged(pair.first) },
                        textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                    )
                }

            }

            ActionButton(
                text = stringResource(id = R.string.next),
                isEnabled = true,
                onClick = { onAction.invoke() },
                modifier = Modifier,
            )
        }
    }

}

