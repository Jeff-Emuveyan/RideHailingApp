package com.example.ride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Type
import com.example.domain.GetRideDetailsUseCase
import com.example.ride.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Boolean

@HiltViewModel
class RideViewModel @Inject constructor(private val getRideDetailsUseCase: GetRideDetailsUseCase) : ViewModel() {
	
	private val _uiState = MutableStateFlow(UiState())
	val uiState: StateFlow<UiState> = _uiState.asStateFlow()
	
	init {
		_uiState.update {
			it.copy(
				showChooseRideModeDialog = true,
				currentLocationOfRider = Pair(6.43094, 3.5428)
			)
		}
	}
	
	fun onOfferRideModeSelected() = viewModelScope.launch {
		_uiState.update {
			it.copy(percentageOfRideDuration = 0, route = emptyList())
		}
		getRideDetailsUseCase(Type.PICKUP).collect { update ->
			val percentageOfRideDuration = update.percentageOfRideDuration
			_uiState.update {
				it.copy(
					percentageOfRideDuration = update.percentageOfRideDuration,
					currentLocationOfRider = update.currentRiderLocation,
					route = update.route,
					showChooseRideModeDialog = percentageOfRideDuration == null,
					showDrivingToPickupDialog = (percentageOfRideDuration != null && percentageOfRideDuration <= 99),
					showAcceptOrDeclineRideDialog = (percentageOfRideDuration != null && percentageOfRideDuration == 100)
				)
			}
		}
	}
	
	fun onStartDropOff() = viewModelScope.launch {
		_uiState.update {
			it.copy(
				percentageOfRideDuration = 0,
				route = emptyList(),
				showAcceptOrDeclineRideDialog = false,
			)
		}
		getRideDetailsUseCase(Type.DROP_OFF).collect { update ->
			val percentageOfRideDuration = update.percentageOfRideDuration
			_uiState.update {
				it.copy(
					percentageOfRideDuration = update.percentageOfRideDuration,
					currentLocationOfRider = update.currentRiderLocation,
					route = update.route,
					showDrivingToDropOffDialog = (percentageOfRideDuration != null && percentageOfRideDuration <= 99),
					showRideCompleteDialog = (percentageOfRideDuration != null && percentageOfRideDuration == 100)
				)
			}
		}
	}
	
	fun onNewRideRequest() {
		_uiState.update {
			it.copy(
				showChooseRideModeDialog = true,
				showDrivingToPickupDialog = false,
				showAcceptOrDeclineRideDialog = false,
				showDrivingToDropOffDialog = false,
				showRideCompleteDialog = false,
				currentLocationOfRider = Pair(0.0,0.0),
				percentageOfRideDuration = null,
				route = emptyList()
			)
		}
	}
}