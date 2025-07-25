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
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import com.pelipunto.app.ui.theme.surfaceContainerLight
import com.pelipunto.app.ui.theme.secondaryContainerLight
import com.pelipunto.app.ui.theme.outlineLight

@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val gradientColors = listOf(
        surfaceContainerLight.copy(alpha = 0.85f),
        secondaryContainerLight.copy(alpha = 0.80f)
    )
    val shadowColor = outlineLight.copy(alpha = 0.25f)

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