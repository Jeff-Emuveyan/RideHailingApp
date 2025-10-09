package com.example.ride

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AnimatedProgressBar(
	progress: Int, // 0..100
	modifier: Modifier = Modifier.padding(horizontal = 16.dp),
	carSize: Dp = 60.dp,
	trackHeight: Dp = 8.dp,
	colors: Pair<Long, Long> = Pair(0xFF1E40AF, 0xFF9EC0FF),
	showProgressHead: Boolean = true
) {
	val clamped = progress.coerceIn(0, 100)
	val animatedProgress by animateFloatAsState(
		targetValue = clamped / 100f,
		animationSpec = tween(durationMillis = 600, easing = LinearEasing)
	)
	
	BoxWithConstraints(
		modifier = modifier
			.fillMaxWidth()
			.height(max(carSize, 60.dp)),
		contentAlignment = Alignment.CenterStart
	) {
		val maxWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
		val carWidthPx = with(LocalDensity.current) { carSize.toPx() }
		// distance the car travels: left-most position is 0, right-most should position car
		// such that its center/leading edge aligns with the end circle. Adjust as desired.
		val travelRangePx = maxWidthPx - carWidthPx - /* space for end circle overlap */ 24
		
		val carOffsetPx = (animatedProgress * travelRangePx).toInt()
		
		// Background (gray) track - full width
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.height(trackHeight)
				.align(Alignment.CenterStart)
				.clip(CircleShape)
				.background(Color(0xFFEAEAEA))
		)
		
		// Gradient progress bar that shrinks from left to right as progress increases.
		// We align to the end and set width to remaining portion.
		Box(
			modifier = Modifier
				.width(with(LocalDensity.current) { (maxWidthPx - (animatedProgress * maxWidthPx)).toDp() })
				.height(trackHeight)
				.align(Alignment.CenterEnd)
				.background(
					Brush.horizontalGradient(
						colors = listOf(Color(colors.first), Color(colors.second))
					)
				)
		)
		
		// End circle (blue with inner dark circle) fixed at the right edge
		if (showProgressHead) {
			Box(
				modifier = Modifier
					.size(24.dp)
					.align(Alignment.CenterEnd),
				contentAlignment = Alignment.Center
			) {
				Box(
					modifier = Modifier
						.size(24.dp)
						.clip(CircleShape)
						.background(Color(0xFF3B82F6))
				)
				Box(
					modifier = Modifier
						.size(10.dp)
						.clip(CircleShape)
						.background(Color(0xFF1E40AF))
				)
			}
		}
		
		// Car image positioned with a fixed pixel offset computed above
		Image(
			painter = painterResource(id = R.drawable.car_progress), // replace with your drawable
			contentDescription = "Car",
			modifier = Modifier
				.size(carSize)
				// apply pixel offset wrapped in IntOffset (no `it` used)
				.offset { IntOffset(x = carOffsetPx, y = 0) }
		)
	}
}
