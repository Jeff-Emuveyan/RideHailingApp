import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.ride.R

private val TIMELINE_CENTER = 32.dp
private val GREEN_COLOR = Color(0xFF4CAF50)
private val ORANGE_COLOR = Color(0xFFFFA500)
private val GRAY_COLOR = Color.Gray
private val DOT_LINE_WIDTH = 2.dp
private val DOT_LINE_HEIGHT = 70.dp
private val DOT_DASH_HEIGHT = 10.dp // Smaller for dot-like appearance
private val DOT_DASH_GAP = 1.dp
private val NODE_HEIGHT = 48.dp
private val PROFILE_SIZE = 24.dp
private val SMALL_PROFILE_SIZE = 14.dp
private val PROFILE_OVERLAP = (-12).dp // For overlapping multiple profiles

@Composable
fun RideTimeline() {
	Row {
		Column(modifier = Modifier.padding(vertical = 16.dp)) {
			TimelineCircle(
				size = SMALL_PROFILE_SIZE,
				color = Color.White,
				filled = false,
				borderColor = Color.Black,
				modifier = Modifier
					.padding(start = 16.dp)
			)
			DottedLine(color = GREEN_COLOR)
			Image(
				painter = painterResource(id = R.drawable.woman),
				contentDescription = "Left Profile",
				modifier = Modifier
					.padding(start = 16.dp)
					.size(SMALL_PROFILE_SIZE)
					.clip(CircleShape)
					.border(1.dp, GREEN_COLOR, CircleShape),
				contentScale = ContentScale.Crop
			)
			DottedLine(color = ORANGE_COLOR)
			Image(
				painter = painterResource(id = R.drawable.woman),
				contentDescription = "Left Profile",
				modifier = Modifier
					.padding(start = 16.dp)
					.size(SMALL_PROFILE_SIZE)
					.clip(CircleShape)
					.border(1.dp, GREEN_COLOR, CircleShape),
				contentScale = ContentScale.Crop
			)
			DottedLine(color = GRAY_COLOR)
			TimelineCircle(
				size = SMALL_PROFILE_SIZE,
				color = Color.Black,
				filled = true,
				modifier = Modifier
					.padding(start = 16.dp)
			)
		}
		
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier.fillMaxWidth()
		) {
			Column {
				StartingPointNode(location = "Ladipo Oluwole Street")
				Spacer(modifier = Modifier.height(16.dp))
				DropOffNode(
					number = 1,
					location = stringResource(R.string.community_road),
					leftProfile = R.drawable.woman,
					rightProfiles = listOf(R.drawable.woman),
					borderColor = GREEN_COLOR,
				)
				Spacer(modifier = Modifier.height(16.dp))
				DropOffNode(
					number = 2,
					location = stringResource(R.string.community_road),
					leftProfile = R.drawable.woman,
					rightProfiles = listOf(R.drawable.woman),
					borderColor = ORANGE_COLOR,
				)
				Spacer(modifier = Modifier.height(16.dp))
				DestinationNode(location = "Community Road")
			}
			Divider(color = Color.LightGray, thickness = 0.5.dp, modifier = Modifier.width(400.dp))
			TimeBadge(text = stringResource(R.string.through_aromire_str_4_min), Modifier.align(Alignment.CenterEnd))
		}
	}
}

@Composable
private fun TimelineCircle(
	size: Dp,
	color: Color,
	filled: Boolean,
	borderColor: Color? = null,
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
			.size(size)
			.clip(CircleShape)
			.background(if (filled) color else Color.Transparent)
			.let { if (borderColor != null) it.border(1.dp, borderColor, CircleShape) else it }
	)
}

@Composable
private fun DottedLine(
	color: Color,
	length: Dp = DOT_LINE_HEIGHT,
	strokeWidth: Dp = DOT_LINE_WIDTH,
	dashLength: Dp = DOT_DASH_HEIGHT,
	dashGap: Dp = DOT_DASH_GAP,
	isVertical: Boolean = true,
	alignmentPadding: Dp = 22.dp,
	numDots: Int = 4
) {
	var effectiveDashGap = dashGap
	if (numDots != null && numDots > 1) {
		val totalDashLength = numDots.toFloat().times(dashLength)
		val totalGapLength = length - totalDashLength
		if (totalGapLength > 0.dp) {
			effectiveDashGap = totalGapLength / (numDots - 1)
		}
		// If totalGapLength <= 0, keep original dashGap, which may result in fewer dots
	}
	
	val modifier = if (isVertical) {
		Modifier
			.padding(start = alignmentPadding)
			.height(length)
			.width(strokeWidth)
	} else {
		Modifier
			.padding(top = alignmentPadding)
			.width(length)
			.height(strokeWidth)
	}
	
	Canvas(modifier = modifier) {
		val dashPx = dashLength.toPx()
		val gapPx = effectiveDashGap.toPx()
		var currentPos = 0f
		val sizeDim = if (isVertical) size.height else size.width
		while (currentPos < sizeDim) {
			val endPos = (currentPos + dashPx).coerceAtMost(sizeDim)
			if (isVertical) {
				drawLine(
					color = color,
					start = Offset(0f, currentPos),
					end = Offset(0f, endPos),
					cap = StrokeCap.Round,
					strokeWidth = strokeWidth.toPx()
				)
			} else {
				drawLine(
					color = color,
					start = Offset(currentPos, 0f),
					end = Offset(endPos, 0f),
					cap = StrokeCap.Round,
					strokeWidth = strokeWidth.toPx()
				)
			}
			currentPos += dashPx + gapPx
		}
	}
}

@Composable
private fun StartingPointNode(location: String) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(NODE_HEIGHT)
			.padding(start = 16.dp)
	) {
		Column(modifier = Modifier.align(Alignment.CenterStart)
		) {
			Text(
				text = stringResource(R.string.starting_point),
				color = Color.Gray,
				fontSize = 10.sp
			)
			Text(
				text = location,
				color = Color.Black,
				fontSize = 12.sp,
			)
		}
	}
}

@Composable
private fun DropOffNode(
	number: Int,
	location: String,
	@DrawableRes leftProfile: Int,
	rightProfiles: List<Int>,
	borderColor: Color,
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(NODE_HEIGHT)
			.padding(start = 16.dp)
	) {
		Column(
			modifier = Modifier
				.align(Alignment.CenterStart)
		) {
			Text(
				text = "Drop-off $number",
				color = Color.Gray,
				fontSize = 10.sp
			)
			Text(
				text = location,
				color = Color.Black,
				fontSize = 12.sp,
			)
		}
		Row(
			modifier = Modifier
				.align(Alignment.CenterEnd)
				.padding(end = 16.dp),
			horizontalArrangement = Arrangement.spacedBy(PROFILE_OVERLAP)
		) {
			rightProfiles.forEach { profile ->
				Image(
					painter = painterResource(id = profile),
					contentDescription = "Right Profile",
					modifier = Modifier
						.size(PROFILE_SIZE)
						.clip(CircleShape)
						.border(1.dp, borderColor, CircleShape),
					contentScale = ContentScale.Crop
				)
			}
		}
	}
}

@Composable
private fun TimeBadge(text: String, modifier: Modifier) {
	Surface(
		modifier = modifier
			.padding(end = 48.dp)
			.wrapContentWidth(),
		shape = RoundedCornerShape(16.dp),
		color = Color(0xFFF5F5F5) // Light gray
	) {
		Text(
			text = text,
			modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
			color = Color.Black,
			fontSize = 10.sp
		)
	}
}

@Composable
private fun DestinationNode(location: String) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(NODE_HEIGHT)
			.padding(start = 16.dp)
	) {
		Column(
			modifier = Modifier
				.align(Alignment.CenterStart)
		) {
			Text(
				text = "Destination",
				color = Color.Gray,
				fontSize = 12.sp
			)
			Text(
				text = location,
				color = Color.Black,
				fontSize = 12.sp,
			)
		}
	}
}