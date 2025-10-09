package com.example.ride

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.ride.dialog.pickup.PickUpDialog
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PickUpDialogTest {
	
	@get:Rule
	val composeTestRule = createComposeRule()
	
	@Test
	fun pickupDialog_displaysAllKeyElements_whenVisible() {
		val appContext = InstrumentationRegistry.getInstrumentation().targetContext
		
		composeTestRule.setContent {
			PickUpDialog(visible = true, progress = 50)
		}
		
		// Wait for any animations (ModalBottomSheet entry)
		composeTestRule.waitForIdle()
		
		// Verify top texts and key UI components exist
		composeTestRule.onNodeWithText(
			appContext.getString(R.string.get_to_pickup)
		).assertIsDisplayed()
		
		composeTestRule.onNodeWithText(
			appContext.getString(R.string._4_min_away)
		).assertIsDisplayed()
		
		composeTestRule.onNodeWithText(
			appContext.getString(R.string.to_pick)
		).assertIsDisplayed()
		
		composeTestRule.onNodeWithText(
			appContext.getString(R.string.darrell_steward)
		).assertIsDisplayed()
		
		composeTestRule.onNodeWithText(
			appContext.getString(R.string.pick_up_point)
		).assertIsDisplayed()
		
		composeTestRule.onNodeWithText(
			appContext.getString(R.string.ladipo_oluwole_street)
		).assertIsDisplayed()
		
		composeTestRule.onNodeWithText(
			appContext.getString(R.string.available_seats)
		).assertIsDisplayed()
		
		composeTestRule.onNodeWithText(
			appContext.getString(R.string.passengers_accepted)
		).assertIsDisplayed()
		
		composeTestRule.onNodeWithText("Share Ride Info")
			.assertIsDisplayed()
			.assertHasClickAction()
	}
	
	@Test
	fun pickupDialog_isNotDisplayed_whenVisibleIsFalse() {
		composeTestRule.setContent {
			PickUpDialog(visible = false, progress = 80)
		}
		
		composeTestRule.waitForIdle()
		
		// Nothing should be rendered on screen
		composeTestRule.onAllNodes(isRoot()).onFirst().assertExists()
		composeTestRule.onAllNodesWithText("Share Ride Info").assertCountEquals(0)
	}
}
