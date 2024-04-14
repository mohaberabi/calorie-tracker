package com.example.tracker_presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.core_ui.LocalSpacing
import java.nio.file.WatchEvent

@Composable
fun UnitDisplay(
    modifier: Modifier = Modifier,
    amount: Int,
    unit: String,
    amountColor: Color = MaterialTheme.colors.onBackground,
    unitTextSize: TextUnit = 14.sp,
    unitColor: Color = MaterialTheme.colors.onBackground
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = modifier
    ) {

        Text(
            text = amount.toString(),
            style = MaterialTheme.typography.h1,
            color = amountColor,
            modifier = Modifier.alignBy(LastBaseline),
        )

        Spacer(modifier = Modifier.width(spacing.xs))

        Text(
            text = unit,
            style = MaterialTheme.typography.h1,
            color = amountColor,
            fontSize = unitTextSize,
            modifier = Modifier.alignBy(LastBaseline),
        )
    }
}