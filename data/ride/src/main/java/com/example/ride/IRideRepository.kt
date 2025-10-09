package com.example.ride

import com.example.core.model.Type
import com.example.ride.model.LocalLocationUpdate
import kotlinx.coroutines.flow.Flow

interface IRideRepository {
	
	fun getRideUpdates(type: Type): Flow<LocalLocationUpdate>
}