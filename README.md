# Ride-hailing Android app

<p float="left">
<img src="https://firebasestorage.googleapis.com/v0/b/voiceoffreedom-9cdf9.appspot.com/o/c.png?alt=media&token=5066b0f3-fe4c-4ca3-ac9e-a359a4bec725" width="300" height="650" />
&nbsp;
  &nbsp;
  <img src="https://firebasestorage.googleapis.com/v0/b/voiceoffreedom-9cdf9.appspot.com/o/a.png?alt=media&token=bd206d0e-e3de-4088-b395-0d28071df3f0" width="300" height="650" />
  &nbsp;
  &nbsp;
  <img src="https://firebasestorage.googleapis.com/v0/b/voiceoffreedom-9cdf9.appspot.com/o/b.png?alt=media&token=840a9c5d-7be6-4ad4-8465-987bd87768e3" width="300" height="650" /> 
</p>

This project is a ride hailing Android application that allows users to either offer rides as drivers or join rides as passengers. The app features an integrated real-time map that displays live location updates of the rider throughout the trip.

Passengers can easily track the driver’s progress and, at the end of the ride, indicate whether the rider arrived or did not arrive, ensuring a transparent and reliable experience for all users.

Here is a screen recording of the entire workflow of the application:

[![Watch the video](https://firebasestorage.googleapis.com/v0/b/voiceoffreedom-9cdf9.appspot.com/o/Screen_recording_20251013_144526.webm?alt=media&token=2c911408-243e-4175-8823-07ec16cdb45f)

<a href="https://firebasestorage.googleapis.com/v0/b/voiceoffreedom-9cdf9.appspot.com/o/Screen_recording_20251013_144526.webm?alt=media&token=2c911408-243e-4175-8823-07ec16cdb45f" target="_blank">
  <img src="https://firebasestorage.googleapis.com/v0/b/voiceoffreedom-9cdf9.appspot.com/o/c.png?alt=media&token=5066b0f3-fe4c-4ca3-ac9e-a359a4bec725" alt="Watch the video" width="600">
</a>

### Build & Run Requirements
To successfully build and run this project, ensure that you have the following setup:
1) Android Studio: Narwhal (or newer)
2) Java Version: Java 21.0.6
3) Minimum Android SDK: API Level 24 (Android 7.0)

For the best **UI experience** and proper layout rendering, it is recommended to run the project using one of the following emulators:
1) Google Pixel 9 Pro Emulator (preferred)
2) Google Pixel 8 Pro Emulator
3) Google Pixel 7 Pro Emulator

### Architecture

While developing this application, I used the **Google recommended** [android app architecture](https://developer.android.com/topic/architecture#recommended-app-arch) 
as a guideline to organize the code base into three distint layers: UI(feature) layer, Domain layer and Data layer.

The UI layer of the app is written completely in Jetpack Compose. There are no Fragments, only Composable screens.

### Module Structure

I also followed the **Google recommended** [modularization structure](https://developer.android.com/topic/modularization/patterns#types-of-modules),
and I grouped the code base into three main modules: 
1) ```app```: This is the container module of the application. Its sole duty is to display all features of the application
2) ```core```: The core module serves as the base module. It contains code that can be resued across any part of the code base.
3) ```feature```: The feature module contains all features of the application. In this case, we only have one feature with is offer ride.
4) ```data```: contains all data related classes of the application.
5) ```domain```: contains the core business logic and use cases that define what the app does, independent of any UI or data framework.

### Tests

The code base contains unit tests and UI test for the Composable screens. These can be found here:
#### Unit tests:
1) https://github.com/Jeff-Emuveyan/RideHailingApp/blob/main/data/ride/src/test/java/com/example/ride/RideRepositoryTest.kt
2) https://github.com/Jeff-Emuveyan/RideHailingApp/blob/main/domain/src/test/java/com/example/domain/GetRideDetailsUseCaseTest.kt
3) https://github.com/Jeff-Emuveyan/RideHailingApp/blob/main/feature/ride/src/test/java/com/example/ride/RideViewModelTest.kt
#### UI tests:
1) https://github.com/Jeff-Emuveyan/RideHailingApp/blob/main/feature/ride/src/androidTest/java/com/example/ride/ChooseRideModeDialogTest.kt
2) https://github.com/Jeff-Emuveyan/RideHailingApp/blob/main/feature/ride/src/androidTest/java/com/example/ride/PickUpDialogTest.kt

### Something extra! 😁

It is very important to monitor the performance of an android app so that issues such as slow startup time and UI jank can be spotted early and resolved.

Because of this, I wrote a [macrobenchmark](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview) test to monitor the startup time
of the app.

This test can be found here:
https://github.com/Jeff-Emuveyan/RideHailingApp/blob/main/benchmark/src/main/java/com/example/benchmark/ExampleStartupBenchmark.kt

### Automation testing 🤖

I also added automation test using [UIAutomator](https://developer.android.com/training/testing/other-components/ui-automator) to automate the end to end flow of the application from when the clicks on the button to book a ride to when the ride has completed successfully.

The automation test can be found here: https://github.com/Jeff-Emuveyan/RideHailingApp/blob/main/app/src/androidTest/java/com/example/assessment/AutomationTest.kt
