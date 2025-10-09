package com.example.ride.model

data class LocalLocationUpdate(
	val currentRiderLocation: Pair<Double, Double>,
	val percentageOfRideDuration: Int,
	val route: List<Pair<Double, Double>>
)