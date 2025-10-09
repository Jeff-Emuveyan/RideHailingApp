package com.example.ride.dialog.dropoff

import RideTimeline
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ride.AnimatedProgressBar
import com.example.ride.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DropOffDialog(visible: Boolean, progress: Int) {
	if (visible.not()) return
	val sheetState = rememberModalBottomSheetState(
		confirmValueChange = { newValue ->
			newValue != SheetValue.Hidden
		}
	)
	
	ModalBottomSheet(
		dragHandle = null,
		onDismissRequest = {},
		sheetState = sheetState,
		scrimColor = Color.Transparent
	) {
		Content(progress)
	}
}

@Composable
private fun Content(progress: Int) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
			.padding(vertical = 16.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			Modifier
				.width(80.dp)
				.height(5.dp)
				.clip(RoundedCornerShape(50))
				.background(colorResource(R.color.divider))
		)
		Header()
		AnimatedProgressBar(
			modifier = Modifier,
			progress = progress,
			showProgressHead = false,
			colors = Pair(0xFF60B527, 0xFF60B527)
		)
		RideTimeline()
		Divider(color = Color.LightGray, thickness = 0.5.dp)
		Spacer(Modifier.height(8.dp))
		FifthSection()
		Spacer(Modifier.height(24.dp))
		ShareRideButton()
	}
}

@Composable
private fun Header() {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = Modifier
			.padding(horizontal = 16.dp)
			.fillMaxWidth()
	) {
		Text(
			text = stringResource(R.string.heading_to),
			fontWeight = FontWeight.Bold,
			fontSize = 16.sp,
			color = Color.Black,
		)
		Column {
			Text(
				text = stringResource(R.string.community_road),
				fontSize = 12.sp,
				color = Color.Black,
			)
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Center
			) {
				Text(
					text = stringResource(R.string.to_drop_off),
					fontStyle = FontStyle.Italic,
					fontSize = 12.sp,
					color = Color.Gray,
				)
				Image(
					painter = painterResource(id = R.drawable.woman),
					contentDescription = "Left Profile",
					modifier = Modifier
						.padding(start = 4.dp)
						.size(14.dp)
						.clip(CircleShape)
						.border(1.dp, Color(0xFFFFA500), CircleShape),
					contentScale = ContentScale.Crop
				)
			}
		}
	}
}

@Composable
private fun FifthSection() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(stringResource(R.string.available_seats), color = Color.Gray, fontSize = 10.sp)
		Text("2", fontWeight = FontWeight.Bold, fontSize = 16.sp)
		Text(stringResource(R.string.passengers_accepted), color = Color.Gray, fontSize = 10.sp)
		PassengerAvatars()
	}
}

@Composable
private fun PassengerAvatars() {
	Row {
		Image(
			painter = painterResource(id = R.drawable.avatar_two),
			contentDescription = null,
			modifier = Modifier
				.size(40.dp)
				.clip(CircleShape)
		)
		Image(
			painter = painterResource(id = R.drawable.woman),
			contentDescription = null,
			modifier = Modifier
				.size(40.dp)
				.clip(CircleShape)
		)
		Box(
			modifier = Modifier
				.size(40.dp)
				.clip(CircleShape)
				.background(Color(0xFFE0E0E0))
				.offset(x = (- 16).dp),
			contentAlignment = Alignment.Center
		) {
		}
	}
}

@Composable
private fun ShareRideButton() {
	Button(
		onClick = {},
		shape = RoundedCornerShape(50),
		border = BorderStroke(1.dp, Color.Black),
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp)
			.height(48.dp),
		colors = ButtonDefaults.buttonColors(containerColor = Color.White)
	) {
		Text("Share Ride Info", color = Color.Black, fontSize = 14.sp)
	}
}