package com.example.onboarding_presentation.composes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun ActionButton(
    modifier: Modifier = Modifier,

    text: String,
    onClick: () -> Unit = {},
    isEnabled: Boolean,
    textStyle: TextStyle = MaterialTheme.typography.button,

    ) {


    Button(
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(100.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = textStyle,
            color = MaterialTheme.colors.onPrimary,
//            modifier = Modifier.padding(LocalSpacing.current.sm)
        )
    }

}