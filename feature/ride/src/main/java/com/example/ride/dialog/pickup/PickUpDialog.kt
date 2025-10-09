package com.example.ride.dialog.pickup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ride.AnimatedProgressBar
import com.example.ride.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PickUpDialog(visible: Boolean, progress: Int) {
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
		RideInfoBottomSheet(progress = progress)
	}
}

@Composable
private fun RideInfoBottomSheet(progress: Int) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
			.padding(vertical = 16.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		RideHeaderSection()
		ProgressIndicator(progress = progress)
		Divider(color = Color.LightGray, thickness = 0.5.dp)
		Spacer(Modifier.height(8.dp))
		RideDetailsCard()
		Spacer(Modifier.height(16.dp))
		SeatAndPassengersSection()
		Spacer(Modifier.height(24.dp))
		ShareRideButton()
	}
}

@Composable
private fun RideHeaderSection() {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			Modifier
				.width(80.dp)
				.height(5.dp)
				.clip(RoundedCornerShape(50))
				.background(colorResource(R.color.divider))
		)
		Spacer(Modifier.height(16.dp))
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.padding(horizontal = 16.dp)
				.fillMaxWidth()
		) {
			Text(
				text = stringResource(R.string.get_to_pickup),
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp,
				color = Color.Black,
			)
			EtaBadge(
				stringResource(R.string._4_min_away),
				colorResource(com.example.ride.R.color.eta_blue),
				image = R.drawable.clock
			)
		}
	}
}

@Composable
private fun EtaBadge(text: String? = null, color: Color, image: Int? = null, textDesign: @Composable () -> Unit = {}) {
	Surface(
		color = color,
		shape = RoundedCornerShape(16.dp)
	) {
		Row(
			modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			if (image != null)
				Image(
					painterResource(com.example.ride.R.drawable.clock),
					""
				)
				Spacer(Modifier.width(4.dp))
		if (text != null)
			Text(text, color = Color.Black, fontSize = 12.sp)
		textDesign()
		}
	}
}

@Composable
private fun ProgressIndicator(progress: Int) {
	AnimatedProgressBar(progress)
}


@Composable
private fun RideDetailsCard() {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		shape = RoundedCornerShape(16.dp),
		border = BorderStroke(5.dp, colorResource(R.color.border_blue)),
		tonalElevation = 2.dp,
		color = Color.White
	) {
		Column(
			modifier = Modifier.fillMaxWidth()
		) {
			Text(
				text = stringResource(R.string.to_pick),
				color = Color.Black,
				fontSize = 10.sp,
				modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp)
			)
			DriverInfoRow()
			Spacer(Modifier.height(8.dp))
			PickupPointSection()
		}
	}
}

@Composable
private fun DriverInfoRow() {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
	) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Image(
				painter = painterResource(id = R.drawable.avatar),
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
						text = stringResource(R.string.darrell_steward),
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

@Composable
private fun PickupPointSection() {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.fillMaxWidth()
			.background(colorResource(R.color.pale_green), shape = RoundedCornerShape(12.dp))
			.padding(horizontal = 12.dp, vertical = 10.dp)
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Image(painter = painterResource(R.drawable.circle), "")
			Spacer(Modifier.height(1.dp))
			Image(painter = painterResource(R.drawable.undercircle), "")
		}
		Spacer(Modifier.width(16.dp))
		Column(Modifier.weight(1f)) {
			Text(stringResource(R.string.pick_up_point), color = Color.Gray, fontSize = 10.sp)
			Text(stringResource(R.string.ladipo_oluwole_street), fontWeight = FontWeight.Medium, fontSize = 12.sp)
		}
		EtaBadge(color = colorResource(R.color.eta_green), textDesign = {
			Text(
				buildAnnotatedString {
					withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
						append(stringResource(R.string.eta))
					}
					append(stringResource(R.string._4_mins))
				},
				fontSize = 14.sp,
				color = Color.Black
			)
		})
	}
}

@Composable
private fun SeatAndPassengersSection() {
	Row(
		modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(stringResource(R.string.available_seats), color = Color.Gray, fontSize = 10.sp)
		Text("1", fontWeight = FontWeight.Bold, fontSize = 16.sp)
		Text(stringResource(R.string.passengers_accepted), color = Color.Gray, fontSize = 10.sp)
		PassengerAvatars()
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
			painter = painterResource(id = com.example.ride.R.drawable.avatar_three),
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
		onClick = { /* share ride */ },
		shape = RoundedCornerShape(50),
		modifier = Modifier
			.width(240.dp)
			.height(48.dp),
		colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
	) {
		Text("Share Ride Info", color = Color.White, fontSize = 14.sp)
	}
}