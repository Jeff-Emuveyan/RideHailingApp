plugins {
	kotlin("kapt")
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
}

android {
	namespace = "com.example.ride"
	compileSdk = 36
	
	defaultConfig {
		minSdk = 28
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
	}
	
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	buildFeatures {
		compose = true
	}
	
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	
	packaging {
		resources {
			excludes += setOf(
				"META-INF/LICENSE.md",
				"META-INF/LICENSE-notice.md",
				"META-INF/LICENSE.txt",
				"META-INF/NOTICE.txt",
				"META-INF/NOTICE.md"
			)
		}
	}
}

dependencies {
	implementation(project(":domain"))
	implementation(project(":core"))
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	// Google Maps SDK for Android
	implementation(libs.play.services.maps)
	// Jetpack Compose Maps SDK
	implementation(libs.maps.compose)
	// hilt
	implementation(libs.hilt)
	kapt(libs.hilt.compiler)
	// hilt test
	kaptTest(libs.hilt.compiler)
	androidTestImplementation(libs.hilt.android.testing)
	kaptAndroidTest(libs.hilt.compiler)
	// viewmodel compose
	implementation(libs.viewmodel.compose)
	implementation(libs.androidx.ui.tooling.preview)
	// hilt navigation compose
	implementation(libs.hilt.navigation.compose)
	testImplementation("io.mockk:mockk:1.13.12")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
	androidTestImplementation("io.mockk:mockk-android:1.13.12")
	testImplementation("app.cash.turbine:turbine:1.1.0")
	testImplementation("junit:junit:4.13.2")
	
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
	androidTestImplementation("androidx.compose.ui:ui-test")
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}