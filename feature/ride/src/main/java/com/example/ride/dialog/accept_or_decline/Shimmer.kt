package com.example.ride.dialog.accept_or_decline

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun Modifier.shimmer(
	cornerRadius: Dp = 0.dp,
	intensity: Float = 0.9f // controls how bright the shimmer streak is
): Modifier {
	val shimmerColors = listOf(
		Color.Transparent,
		Color.White.copy(alpha = intensity),
		Color.Transparent
	)
	
	val transition = rememberInfiniteTransition(label = "Shimmer")
	val translateAnim by transition.animateFloat(
		initialValue = -400f,
		targetValue = 1200f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 1600,
				easing = LinearEasing
			)
		),
		label = "Translate"
	)
	
	return this.drawWithCache {
		val brush = Brush.linearGradient(
			colors = shimmerColors,
			start = Offset(translateAnim, 0f),
			end = Offset(translateAnim + size.width / 2f, size.height)
		)
		val cornerPx = cornerRadius.toPx()
		
		onDrawWithContent {
			// ✅ Draw original composable content first
			drawContent()
			
			// ✅ Draw shimmer as a transparent overlay
			drawRoundRect(
				brush = brush,
				cornerRadius = CornerRadius(cornerPx, cornerPx),
				alpha = 0.6f // make shimmer subtle
			)
		}
	}
}
