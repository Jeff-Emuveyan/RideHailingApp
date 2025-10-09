package com.example.ride.model

data class UiState(
	val showChooseRideModeDialog: Boolean = false,
	val showDrivingToPickupDialog: Boolean = false,
	val showAcceptOrDeclineRideDialog: Boolean = false,
	val showDrivingToDropOffDialog: Boolean = false,
	val showRideCompleteDialog: Boolean = false,
	val currentLocationOfRider: Pair<Double, Double> = Pair(0.0,0.0),
	val percentageOfRideDuration: Int? = null,
	val route: List<Pair<Double, Double>> = emptyList()
) {
	fun getDestinationLocation(): Pair<Double, Double> {
		if (route.isEmpty()) return Pair(0.0,0.0)
		return route.last()
	}
}