package com.example.core.model

data class LocationUpdate(
	val currentRiderLocation: Pair<Double, Double>,
	val percentageOfRideDuration: Int?,
	val route: List<Pair<Double, Double>>
)