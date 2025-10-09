package com.example.ride

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.ride.dialog.choose_ride_mode.ChooseRideModeDialog
import org.junit.Rule
import org.junit.Test

class ChooseRideModeDialogTest {
	
	@get:Rule
	val composeTestRule = createComposeRule()
	
	@Test
	fun bottomSheet_isVisible_whenVisibleIsTrue() {
		composeTestRule.setContent {
			ChooseRideModeDialog(visible = true, onOfferRideModeSelected = {})
		}
		
		// Verify that the bottom sheet content is displayed
		composeTestRule.onNodeWithText("Choose your ride mode").assertIsDisplayed()
		composeTestRule.onNodeWithText("Join a Ride").assertIsDisplayed()
		composeTestRule.onNodeWithText("Offer Ride").assertIsDisplayed()
	}
	
	@Test
	fun clickingOfferRide_triggersCallback() {
		var clicked = false
		
		composeTestRule.setContent {
			ChooseRideModeDialog(visible = true, onOfferRideModeSelected = {
				clicked = true
			})
		}
		
		// Tap on "Offer Ride"
		composeTestRule.onNodeWithText("Offer Ride").performClick()
		
		// Verify callback was triggered
		assert(clicked)
	}
	
	@Test
	fun bottomMenu_displays_all_menu_items() {
		composeTestRule.setContent {
			ChooseRideModeDialog(visible = true, onOfferRideModeSelected = {})
		}
		
		composeTestRule.onNodeWithText("Home").assertIsDisplayed()
		composeTestRule.onNodeWithText("History").assertIsDisplayed()
		composeTestRule.onNodeWithText("Profile").assertIsDisplayed()
	}
	
	@Test
	fun textFields_and_static_texts_are_displayed_correctly() {
		composeTestRule.setContent {
			ChooseRideModeDialog(visible = true, onOfferRideModeSelected = {})
		}
		
		composeTestRule.onNodeWithText("1 Active campaign").assertIsDisplayed()
		composeTestRule.onNodeWithText("Where to?").assertIsDisplayed()
	}
}
