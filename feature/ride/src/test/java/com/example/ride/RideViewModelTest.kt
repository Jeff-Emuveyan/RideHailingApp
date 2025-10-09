package com.example.ride

import com.example.core.model.LocationUpdate
import com.example.core.model.Type
import com.example.domain.GetRideDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RideViewModelTest {
	
	private val mockUseCase = mockk<GetRideDetailsUseCase>()
	private lateinit var viewModel: RideViewModel
	
	private val testDispatcher = StandardTestDispatcher()
	
	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
	}
	
	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}
	
	@Test
	fun `onOfferRideModeSelected should update uiState as flow emits`() = runTest {
		// Arrange
		val fakeFlow = flowOf(
			LocationUpdate(currentRiderLocation = 1.0 to 2.0, percentageOfRideDuration = 10, route = listOf(1.0 to 2.0))
		)
		coEvery { mockUseCase(Type.PICKUP) } returns fakeFlow
		
		viewModel = RideViewModel(mockUseCase)
		
		// Act
		viewModel.onOfferRideModeSelected()
		
		// Let all coroutines finish
		advanceUntilIdle()
		
		// Assert
		val state = viewModel.uiState.value
		assertEquals(10, state.percentageOfRideDuration)
		assertTrue(state.showDrivingToPickupDialog)
	}
	
	
	@Test
	fun `onNewRideRequest should reset all ui flags`() = runTest {
		// Arrange
		coEvery { mockUseCase(Type.PICKUP) } returns flowOf()
		viewModel = RideViewModel(mockUseCase)
		
		// Act
		viewModel.onNewRideRequest()
		
		// Assert
		val newState = viewModel.uiState.value
		assertTrue(newState.showChooseRideModeDialog)
		assertFalse(newState.showDrivingToPickupDialog)
		assertFalse(newState.showAcceptOrDeclineRideDialog)
		assertEquals(0.0 to 0.0, newState.currentLocationOfRider)
		assertEquals(null, newState.percentageOfRideDuration)
	}
}
