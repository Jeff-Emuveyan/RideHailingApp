plugins {
	kotlin("kapt")
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.hilt)
	alias(libs.plugins.serialization)
}

android {
	namespace = "com.example.assessment"
	compileSdk = 36
	
	defaultConfig {
		applicationId = "com.example.assessment"
		minSdk = 28
		targetSdk = 36
		versionCode = 1
		versionName = "1.0"
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
		create("benchmark") {
			initWith(buildTypes.getByName("release"))
			signingConfig = signingConfigs.getByName("debug")
			matchingFallbacks += listOf("release")
			isDebuggable = false
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
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
	implementation(project(":feature:ride"))
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
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	// Google Maps SDK for Android
	implementation("com.google.android.gms:play-services-maps:19.0.0")
	
	// Jetpack Compose Maps SDK
	implementation("com.google.maps.android:maps-compose:4.4.1")
	
	// hilt
	implementation(libs.hilt)
	kapt(libs.hilt.compiler)
	kapt(libs.androidx.hilt.compiler)
	// hilt test
	kaptTest(libs.hilt.compiler)
	androidTestImplementation(libs.hilt.android.testing)
	kaptAndroidTest(libs.hilt.compiler)
	// serialization
	implementation(libs.kotlinx.serialization.json)
	
	androidTestImplementation("androidx.test.uiautomator:uiautomator:2.4.0-alpha05")
}