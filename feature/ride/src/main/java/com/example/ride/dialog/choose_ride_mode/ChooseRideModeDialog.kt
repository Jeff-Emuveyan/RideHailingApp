package com.example.ride.dialog.choose_ride_mode

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ride.R
import com.example.ride.dialog.choose_ride_mode.model.RideMode
import com.example.ride.util.vibrate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChooseRideModeDialog(visible: Boolean, onOfferRideModeSelected: () -> Unit) {
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
		Column(Modifier.background(Color.White)) {
			Content(onOfferRideModeSelected = onOfferRideModeSelected)
			BottomMenu()
		}
	}
}

@Composable
private fun BottomMenu() {
	val menuItems = listOf(
		Pair(R.drawable.house, R.string.menu_home),
		Pair(R.drawable.calendar, R.string.menu_history),
		Pair(R.drawable.linc_profile, R.string.menu_profile)
	)
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceAround
	) {
		menuItems.forEach { menu ->
			Column(horizontalAlignment = Alignment.CenterHorizontally) {
				Image(
					modifier = Modifier.size(20.dp),
					painter = painterResource(id = menu.first),
					contentDescription = ""
				)
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					fontSize = 10.sp,
					text = stringResource(id = menu.second)
				)
			}
		}
	}
}

@Composable
private fun Content(onOfferRideModeSelected: () -> Unit) {
	Column(
		modifier = Modifier
			.background(colorResource(R.color.light_green))
			.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Row(
			modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center
		) {
			Image(painter = painterResource(R.drawable.campaign), "")
			Spacer(Modifier.width(8.dp))
			Text(
				modifier = Modifier.fillMaxWidth(),
				text = stringResource(R.string.campaign)
			)
		}
		RideModeBottomSheet(onOfferRideModeSelected = onOfferRideModeSelected)
	}
}

@Composable
private fun RideModeBottomSheet(onOfferRideModeSelected: () -> Unit) {
	val context = LocalContext.current
	var selectedMode by remember { mutableStateOf(RideMode.JOIN) }
	
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
			.padding(horizontal = 24.dp, vertical = 10.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			Modifier
				.width(80.dp)
				.height(5.dp)
				.clip(RoundedCornerShape(50))
				.background(colorResource(R.color.divider))
		)
		Spacer(Modifier.height(32.dp))
		Text(
			text = stringResource(R.string.choose_your_ride_mode),
			fontSize = 18.sp,
			color = Color.Black,
			fontWeight = FontWeight.Bold
		)
		Spacer(Modifier.height(24.dp))
		RideModeSelector(
			selectedMode = selectedMode,
			onSelect = {
				selectedMode = it
				if (it == RideMode.OFFER) onOfferRideModeSelected()
			}
		)
		Spacer(Modifier.height(16.dp))
		Surface(
			tonalElevation = 2.dp,
			shape = RoundedCornerShape(24.dp),
			modifier = Modifier.fillMaxWidth()
		) {
			Row(
				modifier = Modifier.background(Color(0xFFF8F8F8))
					.padding(horizontal = 16.dp, vertical = 12.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				Image(
					painter = painterResource(id = R.drawable.routing),
					contentDescription = null,
				)
				Spacer(Modifier.width(8.dp))
				Text(
					text = "Where to?",
					color = Color.Black,
					fontSize = 16.sp
				)
			}
		}
	}
	LaunchedEffect(selectedMode) {
		if (selectedMode == RideMode.OFFER)
			vibrate(context, 2_000)
	}
}

@Composable
private fun RideModeSelector(
	selectedMode: RideMode,
	onSelect: (RideMode) -> Unit
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(68.dp)
			.clip(RoundedCornerShape(16.dp))
			.padding(4.dp)
	) {
		BoxWithConstraints {
			val maxWidthPx = constraints.maxWidth.toFloat()
			val indicatorOffset by animateFloatAsState(
				targetValue = if (selectedMode == RideMode.JOIN) 0f else maxWidthPx / 2f,
				label = "indicatorSlide"
			)
			
			Box(
				modifier = Modifier
					.offset { IntOffset(indicatorOffset.toInt(), 0) }
					.width(maxWidth / 2)
					.fillMaxHeight()
					.clip(RoundedCornerShape(12.dp))
					.background(Color(0xFF2563EB))
			)
		}
		
		Row(
			Modifier.fillMaxSize(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			RideModeOption(
				image = R.drawable.join,
				title = stringResource(R.string.join_a_ride),
				subtitle = stringResource(R.string.book_your_seat),
				selected = selectedMode == RideMode.JOIN,
				onClick = { onSelect(RideMode.JOIN) },
				modifier = Modifier.weight(1f)
			)
			RideModeOption(
				image = R.drawable.offer,
				title = stringResource(R.string.offer_ride),
				subtitle = stringResource(R.string.share_your_trip),
				selected = selectedMode == RideMode.OFFER,
				onClick = { onSelect(RideMode.OFFER) },
				modifier = Modifier.weight(1f)
			)
		}
	}
}

@Composable
private fun RideModeOption(
	@DrawableRes image: Int,
	title: String,
	subtitle: String,
	selected: Boolean,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	val textColor by animateColorAsState(if (selected) Color.White else Color.Black)
	
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center,
		modifier = modifier
	) {
		Image(
			modifier = Modifier.size(48.dp).padding(end = 8.dp),
			painter = painterResource(id = image),
			contentDescription = null,
		)
		Column(
			modifier = Modifier
				.clickable { onClick() }
				.padding(vertical = 8.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(title, color = textColor)
			Text(subtitle, fontSize = 12.sp, color = textColor.copy(alpha = 0.8f))
		}
	}
}