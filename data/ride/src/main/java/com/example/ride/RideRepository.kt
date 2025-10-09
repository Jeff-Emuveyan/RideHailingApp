package com.example.ride

import com.example.core.model.Type
import com.example.ride.model.LocalLocationUpdate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class RideRepository @Inject constructor(): IRideRepository {
	
	override fun getRideUpdates(type: Type): Flow<LocalLocationUpdate> = flow {
		val routePoints = if (type == Type.PICKUP) routeToPickUpPoint else routeToDropOff
		val totalPoints = routePoints.size
		routePoints.forEachIndexed { index, location ->
			val percentage = ((index + 1) / totalPoints.toFloat() * 100).toInt()
			delay(1000L)
			emit(
				LocalLocationUpdate(
					currentRiderLocation = location,
					percentageOfRideDuration = percentage,
					route = routePoints
				)
			)
		}
	}
}