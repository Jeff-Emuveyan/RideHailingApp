plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
	id("kotlin-kapt")
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
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
}

dependencies {
	implementation(project(":core"))
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	// hilt
	implementation(libs.hilt)
	kapt(libs.hilt.compiler)
	testImplementation("io.mockk:mockk:1.13.12")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
	androidTestImplementation("io.mockk:mockk-android:1.13.12")
	testImplementation("app.cash.turbine:turbine:1.1.0")
}