package com.example.ride.dialog.accept_or_decline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ride.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AcceptOrDeclineDialog(visible: Boolean, onProceed: () -> Unit) {
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
		Content(onProceed)
	}
}

@Composable
private fun Content(onProceed: () -> Unit) {
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
		FirstSection()
		DriverInfoRow()
		Spacer(Modifier.height(8.dp))
		Divider(color = Color.LightGray, thickness = 0.5.dp)
		Spacer(Modifier.height(8.dp))
		ThirdSection()
		FourthSection(onSwipe = onProceed)
		FifthSection()
		Spacer(Modifier.height(8.dp))
		Divider(color = Color.LightGray, thickness = 0.5.dp)
		Spacer(Modifier.height(16.dp))
		ShareRideButton()
	}
}

@Composable
private fun FirstSection() {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = Modifier
			.padding(horizontal = 16.dp)
			.fillMaxWidth()
	) {
		Text(
			text = stringResource(R.string.rider_is_arriving),
			fontWeight = FontWeight.Bold,
			fontSize = 16.sp,
			color = Color.Black,
		)
		Column {
			Text(
				text = stringResource(R.string._04_45),
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp,
				color = Color.Black,
			)
			Text(
				text = stringResource(R.string.waiting_time),
				fontWeight = FontWeight.Bold,
				fontSize = 12.sp,
				color = Color.Gray,
			)
		}
	}
}

@Composable
private fun DriverInfoRow() {
	Column {
		Text(
			text = stringResource(R.string.to_pick_up),
			color = Color.Black,
			fontSize = 10.sp,
			modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp)
		)
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp),
		) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Image(
					painter = painterResource(id = R.drawable.woman),
					contentDescription = null,
					modifier = Modifier
						.size(40.dp)
						.clip(CircleShape)
				)
				Spacer(Modifier.width(8.dp))
				Column(
					verticalArrangement = Arrangement.spacedBy(0.dp)
				) {
					Row(verticalAlignment = Alignment.CenterVertically) {
						Text(
							text = stringResource(R.string.nneka_chukwu),
							fontSize = 10.sp, color = Color.Black,
							fontWeight = FontWeight.Bold
						)
						Spacer(Modifier.width(4.dp))
						Image(painterResource(R.drawable.green_tick), "")
					}
					Row(verticalAlignment = Alignment.CenterVertically) {
						Image(painterResource(R.drawable.star), "")
						Spacer(Modifier.width(4.dp))
						Text("4.7", fontSize = 10.sp, color = Color.Gray, fontStyle = FontStyle.Italic)
					}
				}
			}
			
			Row {
				Box(
					contentAlignment = Alignment.Center
				) {
					IconButton(onClick = { /* message */ }) {
						Image(
							painter = painterResource(R.drawable.featured_icon),
							contentDescription = null
						)
					}
					Image(
						painter = painterResource(R.drawable.counter),
						contentDescription = null,
						modifier = Modifier
							.align(Alignment.TopEnd)
							.offset(x = (- 4).dp, y = (8).dp)
					)
				}
				IconButton(onClick = { /* call */ }) {
					Image(painterResource(R.drawable.featured_icon__1_), "")
				}
			}
		}
	}
}

@Composable
private fun ThirdSection() {
	Row(
		modifier = Modifier.padding(horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			val lineHeight = 8.dp
			Image(painter = painterResource(R.drawable.circle), "")
			Spacer(Modifier.height(1.dp))
			Image(painter = painterResource(R.drawable.undercircle), "", Modifier.height(lineHeight))
			Spacer(Modifier.height(1.dp))
			Image(painter = painterResource(R.drawable.undercircle), "", Modifier.height(lineHeight))
			Spacer(Modifier.height(1.dp))
			Image(painter = painterResource(R.drawable.undercircle), "", Modifier.height(lineHeight))
		}
		Spacer(Modifier.width(16.dp))
		Column(Modifier.weight(1f)) {
			Text(stringResource(R.string.pick_up_point), color = Color.Gray, fontSize = 10.sp)
			Text(stringResource(R.string.ladipo_oluwole_street), fontWeight = FontWeight.Medium, fontSize = 12.sp)
		}
	}
}

@Composable
private fun PassengerAvatars() {
	Row {
		Image(
			painter = painterResource(id = com.example.ride.R.drawable.avatar_two),
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
private fun FourthSection(onSwipe: () -> Unit,) {
	var offsetX by remember { mutableStateOf(0f) }
	Image(
		painterResource(R.drawable.cta),
		"",
		Modifier
			.shimmer()
			.fillMaxWidth()
			.height(70.dp)
			.pointerInput(Unit) {
				detectHorizontalDragGestures(
					onDragEnd = {
						offsetX = 0f
					},
					onHorizontalDrag = { _, dragAmount ->
						offsetX += dragAmount
						if (offsetX > 100) {
							onSwipe()
							offsetX = 0f
						} else if (offsetX < -100) {
							onSwipe()
							offsetX = 0f
						}
					}
				)
			}.semantics {
				contentDescription = "swipe"
			}
	)
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