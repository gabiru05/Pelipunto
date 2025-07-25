package com.pelipunto.app.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pelipunto.app.movie_detail.domain.models.Review
import com.pelipunto.app.ui.components.CollapsibleText
import com.pelipunto.app.ui.theme.itemSpacing
import kotlin.math.roundToInt

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review
) {
    Column(modifier) {
        val nameAnnotatedString = buildAnnotatedString {
            append(review.author)
            append(" • ")
            append(review.createdAt)
        }
        val ratingAnnotatedString = buildAnnotatedString {
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(review.rating.roundToInt().toString())
            pop()
            pushStyle(SpanStyle(fontSize = 10.sp))
            append("/10")
            pop()
        }
        Text(
            text = nameAnnotatedString,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(itemSpacing))
        CollapsibleText(text = review.content, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(itemSpacing))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(text = ratingAnnotatedString, style = MaterialTheme.typography.bodySmall)
        }
    }
}