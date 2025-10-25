package com.example.assessment

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.textAsString
import androidx.test.uiautomator.uiAutomator
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class AutomationTest {
	
	@Test
	fun testEndToEndFlow() {
		startApp()
		chooseARide()
		acceptRide()
		seesRideCompleteMessage()
	}
	
	fun startApp() {
		uiAutomator {
			startApp("com.example.assessment")
			waitForAppToBeVisible("com.example.assessment")
		}
	}
	
	fun chooseARide() {
		uiAutomator {
			onElement { textAsString() == "Offer Ride" }.click()
		}
	}
	
	fun acceptRide() {
		uiAutomator {
			onElement(timeoutMs = 60_000) { contentDescription == "swipe" }.swipe(Direction.RIGHT, 0.5f)
		}
	}
	
	fun seesRideCompleteMessage() {
		uiAutomator {
			val successMessage = onElementOrNull(timeoutMs = 60_000) { textAsString() == "Trip Complete! Thank You." }
			Assert.assertNotNull(successMessage)
		}
	}
}