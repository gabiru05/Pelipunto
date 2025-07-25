package com.pelipunto.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.surfaceBright.copy(alpha = 0.92f),
        MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
    )
    val shadowColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.18f)

    Box(
        modifier = modifier
            .shadow(
                elevation = 18.dp,
                shape = RoundedCornerShape(32.dp),
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .clip(RoundedCornerShape(32.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors
                )
            )
            .padding(32.dp)
    ) {
        content()
    }
} 