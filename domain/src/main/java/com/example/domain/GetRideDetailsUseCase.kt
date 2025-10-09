package com.example.domain

import com.example.core.model.LocationUpdate
import com.example.core.model.Type
import com.example.ride.IRideRepository
import com.example.ride.model.LocalLocationUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRideDetailsUseCase @Inject constructor(private val repository: IRideRepository) {
	
	operator fun invoke(type: Type): Flow<LocationUpdate> {
		return repository.getRideUpdates(type).map {
			LocationUpdate(
				currentRiderLocation = it.currentRiderLocation,
				percentageOfRideDuration = it.percentageOfRideDuration,
				route = it.route
			)
		}
	}
}