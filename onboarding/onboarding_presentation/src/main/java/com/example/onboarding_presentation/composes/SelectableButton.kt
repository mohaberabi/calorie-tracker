package com.example.onboarding_presentation.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.core_ui.LocalSpacing


@Composable
fun SelectableButton(
    text: String,

    selected: Boolean,
    color: Color,
    selectedColor: Color,
    onPress: () -> Unit = {},
    modifier: Modifier = Modifier,
    textStyle: TextStyle,

    ) {

    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(100.dp)
            )
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(100.dp)
            )
            .background(
                color = if (selected) color else Color.Transparent,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable {
                onPress.invoke()
            }
            .padding(LocalSpacing.current.sm)

    ) {
        Text(
            text = text, style = textStyle,
            color = if (selected) selectedColor else color,

            )
    }
}