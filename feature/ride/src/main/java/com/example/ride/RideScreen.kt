package com.example.ride

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ride.dialog.accept_or_decline.AcceptOrDeclineDialog
import com.example.ride.dialog.choose_ride_mode.ChooseRideModeDialog
import com.example.ride.dialog.dropoff.DropOffDialog
import com.example.ride.dialog.pickup.PickUpDialog
import com.example.ride.model.UiState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun RideScreen(viewModel: RideViewModel = hiltViewModel()) {
	val state = viewModel.uiState.collectAsStateWithLifecycle().value
	RideScreen(
		uiState = state,
		onOfferRideModeSelected = viewModel::onOfferRideModeSelected,
		onProceedToDropOff = viewModel::onStartDropOff,
		onNewRideRequest = viewModel::onNewRideRequest
	)
}

@Composable
internal fun RideScreen(
	uiState: UiState,
	onOfferRideModeSelected: () -> Unit,
	onProceedToDropOff: () -> Unit,
	onNewRideRequest: () -> Unit,
) {
	val coroutineScope = rememberCoroutineScope()
	val riderLocation = LatLng(
		uiState.currentLocationOfRider.first,
		uiState.currentLocationOfRider.second
	)
	val destinationLocation = LatLng(
		uiState.getDestinationLocation().first,
		uiState.getDestinationLocation().second
	)
	val cameraPositionState = rememberCameraPositionState {
		position = CameraPosition.fromLatLngZoom(riderLocation, 15f)
	}
	
	Column {
		GoogleMap(
			modifier = Modifier.fillMaxSize(),
			cameraPositionState = cameraPositionState
		) {
			Route(
				visible = uiState.route.isNotEmpty(),
				route = uiState.route
			)
			Marker(
				state = MarkerState(position = riderLocation),
				title = stringResource(R.string.you),
				icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
			)
			Marker(
				state = MarkerState(position = destinationLocation),
				title = stringResource(R.string.destination),
				icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)
			)
		}
	}
	ChooseRideModeDialog(
		visible = uiState.showChooseRideModeDialog,
		onOfferRideModeSelected = onOfferRideModeSelected
	)
	PickUpDialog(
		visible = uiState.showDrivingToPickupDialog,
		uiState.percentageOfRideDuration ?: 0
	)
	AcceptOrDeclineDialog(
		visible = uiState.showAcceptOrDeclineRideDialog,
		onProceed = onProceedToDropOff
	)
	DropOffDialog(
		visible = uiState.showDrivingToDropOffDialog,
		progress = uiState.percentageOfRideDuration ?: 0
	)
	RideCompleteScreen(
		visible = uiState.showRideCompleteDialog,
		onDismiss = onNewRideRequest
	)
	LaunchedEffect(riderLocation) {
		coroutineScope.launch {
			cameraPositionState.animate(
				update = CameraUpdateFactory.newLatLng(riderLocation),
				durationMs = 500
			)
		}
	}
}

@Composable
private fun Route(visible: Boolean, route: List<Pair<Double, Double>>) {
	if (visible.not()) return
	Polyline(
		points = route.map { (lat, lng) -> LatLng(lat, lng) },
		color = colorResource(R.color.dark_orange),
		width = 14f
	)
}