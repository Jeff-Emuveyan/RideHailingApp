package com.example.ride

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RideCompleteScreen(visible: Boolean, onDismiss: () -> Unit) {
	if (visible.not()) return
	Column(
		Modifier.fillMaxSize().background(Color.White)
	) {
		Content(onDismiss)
	}
}

@Composable
private fun Content(onDismiss: () -> Unit) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(brush = Brush.verticalGradient(listOf(Color(0xFF127146), Color(0xFF1B945D))))
	) {
		TopSection(Modifier.weight(0.48f), onDismiss)
		BottomSection(Modifier.weight(0.55f), onDismiss)
	}
}

@Composable
private fun TopSection(modifier: Modifier, onDismiss: () -> Unit) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Box(modifier = Modifier.fillMaxWidth()) {
				Image(
					painter = painterResource(R.drawable.close_icon),
					contentDescription = "Close",
					modifier = Modifier
						.size(36.dp)
						.align(Alignment.TopStart)
						.clickable { onDismiss() }
				)
			}
			Spacer(modifier = Modifier.height(16.dp))
			CO2CircleIndicator()
			Spacer(modifier = Modifier.height(24.dp))
			Text(
				text = "Trip Complete! Thank You.",
				style = MaterialTheme.typography.headlineSmall.copy(
					color = Color.White,
					fontWeight = FontWeight.Bold
				)
			)
			Spacer(modifier = Modifier.height(8.dp))
			Text(
				text = "Another successful trip, well done!",
				style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.9f))
			)
			Spacer(modifier = Modifier.height(16.dp))
			Row(
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				Image(
					painter = painterResource(R.drawable.sun),
					contentDescription = "Sun Icon",
					modifier = Modifier.size(16.dp)
				)
				Spacer(modifier = Modifier.width(6.dp))
				Text(
					text = "Carbon Emission Avoided: ",
					style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
				)
				Text(
					text = "~1.2 km private car equivalent",
					style = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontStyle = FontStyle.Italic)
				)
			}
			Spacer(modifier = Modifier.height(24.dp))
		}
	}
}

@Composable
private fun CO2CircleIndicator(
	modifier: Modifier = Modifier,
	progress: Float = 0.15f,
	valueText: String = "0.86 kg",
	labelText: String = "CO₂",
	subText: String = "So far this month"
) {
	val animatedProgress by animateFloatAsState(
		targetValue = progress,
		animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
	)
	Box(
		modifier = modifier
			.size(160.dp)
			.background(
				brush = Brush.radialGradient(
					colors = listOf(Color(0xFF146B43), Color(0xFF146B43)),
					center = Offset.Unspecified,
					radius = 400f
				),
				shape = CircleShape
			),
		contentAlignment = Alignment.Center
	) {
		Canvas(modifier = Modifier.fillMaxSize()) {
			val strokeWidth = 8.dp.toPx()
			val diameter = size.minDimension - strokeWidth
			val arcSize = Size(diameter, diameter)
			val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
			
			// Background arc
			drawArc(
				color = Color(0xFF1A8C58),
				startAngle = 0f,
				sweepAngle = 360f,
				useCenter = false,
				topLeft = topLeft,
				size = arcSize,
				style = Stroke(strokeWidth, cap = StrokeCap.Round)
			)
			
			// Foreground progress arc
			drawArc(
				color = Color(0xFF2DFFA0),
				startAngle = -90f,
				sweepAngle = animatedProgress * 360f,
				useCenter = false,
				topLeft = topLeft,
				size = arcSize,
				style = Stroke(strokeWidth, cap = StrokeCap.Round)
			)
			
			// Progress circle thumb
			val angleRad = Math.toRadians((animatedProgress * 360f - 90f).toDouble())
			val radius = diameter / 2
			val center = Offset(size.width / 2, size.height / 2)
			val thumbX = (center.x + radius * cos(angleRad)).toFloat()
			val thumbY = (center.y + radius * sin(angleRad)).toFloat()
			
			drawCircle(
				color = Color(0xFF2DFFA0),
				radius = 10.dp.toPx(),
				center = Offset(thumbX, thumbY)
			)
		}
		
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(
				text = labelText,
				color = Color(0xFF2AE891),
				style = MaterialTheme.typography.bodyMedium.copy(
					fontWeight = FontWeight.Medium
				)
			)
			Text(
				text = valueText,
				style = MaterialTheme.typography.headlineMedium.copy(
					color = Color.White,
				)
			)
			Text(
				text = subText,
				color = Color(0xFF2AE891),
				style = MaterialTheme.typography.bodySmall
			)
		}
	}
}

@Composable
private fun BottomSection(modifier: Modifier, onDismiss: () -> Unit) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.background(
				color = Color.White,
				shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
			)
			.padding(horizontal = 16.dp, vertical = 16.dp)
	) {
		Column(
			modifier = Modifier.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				textAlign = TextAlign.Center,
				color = colorResource(R.color.complete_green),
				text = "You helped 4 riders get to their destinations.",
				modifier = Modifier.align(Alignment.CenterHorizontally)
			)
			Spacer(modifier = Modifier.height(20.dp))
			Text(
				text = "Rate your passengers",
				style = MaterialTheme.typography.titleMedium,
				modifier = Modifier.align(Alignment.CenterHorizontally)
			)
			Spacer(modifier = Modifier.height(16.dp))
			PassengerRatingItem("Wade Warren", "Ladipo Oluwole Street")
			PassengerRatingItem("Brooklyn Simmons", "Ladipo Oluwole Street")
			Spacer(modifier = Modifier.height(24.dp))
			Text(
				text = "Earnings for This Trip",
				style = MaterialTheme.typography.titleMedium,
				modifier = Modifier.align(Alignment.CenterHorizontally)
			)
			Spacer(modifier = Modifier.height(16.dp))
			EarningsRow("Net Earnings", "₦6,500.00")
			Spacer(modifier = Modifier.height(8.dp))
			EarningsRow("Bonus", "₦500.00")
			Spacer(modifier = Modifier.height(8.dp))
			EarningsRow("Linc Commission", "₦500.00")
			Spacer(modifier = Modifier.height(24.dp))
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				OutlinedButton(
					onClick = {},
					modifier = Modifier
						.weight(1f)
						.height(48.dp)
						.padding(end = 8.dp),
					shape = RoundedCornerShape(24.dp),
					colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.complete_ash))
				) {
					Text(
						color = Color.Black,
						text = "Earnings History"
					)
				}
				Button(
					onClick = { onDismiss() },
					modifier = Modifier
						.weight(1f)
						.height(48.dp)
						.padding(start = 8.dp),
					shape = RoundedCornerShape(24.dp),
					colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
				) {
					Text("New Trip")
				}
			}
		}
	}
}

@Composable
private fun PassengerRatingItem(name: String, pickupPoint: String) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Image(
				painter = painterResource(com.example.ride.R.drawable.avatar_two),
				contentDescription = "Passenger Image",
				modifier = Modifier
					.size(40.dp)
					.clip(CircleShape)
			)
			Spacer(modifier = Modifier.width(8.dp))
			Column {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(text = name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
					Spacer(modifier = Modifier.width(4.dp))
					Image(
						painter = painterResource(com.example.ride.R.drawable.green_tick),
						contentDescription = "Verified Icon",
						modifier = Modifier.size(14.dp)
					)
				}
				Row(verticalAlignment = Alignment.CenterVertically) {
					Image(
						painter = painterResource(R.drawable.location),
						contentDescription = "Location Icon",
						modifier = Modifier.size(12.dp)
					)
					Spacer(modifier = Modifier.width(4.dp))
					Text(
						fontSize = 10.sp,
						text = "Pick-up Point: $pickupPoint",
						style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
					)
				}
			}
		}
		Button(
			onClick = {},
			shape = RoundedCornerShape(20.dp),
			colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F3F3))
		) {
			Text("Rate now", color = Color.Black)
		}
	}
}

@Composable
private fun EarningsRow(label: String, value: String) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(label, style = MaterialTheme.typography.bodyMedium)
		Text(value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
	}
}
