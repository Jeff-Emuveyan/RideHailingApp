package com.example.domain

import app.cash.turbine.test
import com.example.core.model.Type
import com.example.ride.IRideRepository
import com.example.ride.model.LocalLocationUpdate
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRideDetailsUseCaseTest {
	
	private val mockRepository = mockk<IRideRepository>()
	private val useCase = GetRideDetailsUseCase(mockRepository)
	
	@Test
	fun `invoke emits mapped LocationUpdate from repository`() = runTest {
		// Arrange
		val fakeLocalUpdates = listOf(
			LocalLocationUpdate(
				currentRiderLocation = 1.0 to 2.0,
				percentageOfRideDuration = 10,
				route = listOf(1.0 to 2.0, 3.0 to 4.0)
			),
			LocalLocationUpdate(
				currentRiderLocation = 5.0 to 6.0,
				percentageOfRideDuration = 50,
				route = listOf(5.0 to 6.0, 7.0 to 8.0)
			)
		)
		
		coEvery { mockRepository.getRideUpdates(Type.PICKUP) } returns flowOf(*fakeLocalUpdates.toTypedArray())
		
		// Act + Assert
		useCase(Type.PICKUP).test {
			val first = awaitItem()
			assertEquals(1.0 to 2.0, first.currentRiderLocation)
			assertEquals(10, first.percentageOfRideDuration)
			assertEquals(listOf(1.0 to 2.0, 3.0 to 4.0), first.route)
			
			val second = awaitItem()
			assertEquals(5.0 to 6.0, second.currentRiderLocation)
			assertEquals(50, second.percentageOfRideDuration)
			assertEquals(listOf(5.0 to 6.0, 7.0 to 8.0), second.route)
			
			awaitComplete()
		}
	}
}
