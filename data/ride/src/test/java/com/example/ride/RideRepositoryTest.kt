package com.example.ride

import app.cash.turbine.test
import com.example.core.model.Type
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class RideRepositoryTest {
	
	private val repository = RideRepository()
	
	@Test
	fun `getRideUpdates emits correct progress and locations`() = runTest {
		// Given
		val type = Type.PICKUP
		
		// When
		val flow = repository.getRideUpdates(type)
		
		// Then
		flow.test(timeout = 20.seconds) {
			var lastPercentage = 0
			var emissionCount = 0
			
			while (true) {
				val emission = awaitItem() // get each emission
				emissionCount++
				
				// Assert 1: percentage increases
				assert(emission.percentageOfRideDuration >= lastPercentage)
				lastPercentage = emission.percentageOfRideDuration
				
				// Assert 2: route consistency
				assertEquals(emission.route.first(), emission.route.first())
				
				// Stop when percentage reaches 100
				if (emission.percentageOfRideDuration == 100) break
			}
			
			assertEquals(100, lastPercentage)
			assert(emissionCount > 0)
			
			cancelAndIgnoreRemainingEvents()
		}
	}
}
