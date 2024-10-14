plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":domain"))
    implementation(project(":core"))

    // Room for local data storage
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    // Retrofit for remote API requests
    implementation(libs.retrofit.v290)
    implementation(libs.converter.gson.v290)

    // Coroutines for async handling
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android.v164)

    // JUnit
    testImplementation(libs.junit)

    // Mockito or MockK for mocking
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockk)

    // Coroutine testing
    testImplementation(libs.kotlinx.coroutines.test)

    // Turbine for testing flows
    testImplementation(libs.turbine)

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //map_location
    implementation(libs.play.services.location)
    implementation (libs.play.services.maps)
    implementation (libs.kotlinx.coroutines.play.services)
    // JUnit for testing
    testImplementation (libs.junit)
    // Mockito for mocking
    testImplementation (libs.mockito.core.v451)
    // Mockito Kotlin extension for easier usage
    testImplementation (libs.mockito.kotlin)
    // Coroutine test support
    testImplementation (libs.kotlinx.coroutines.test.v160)
}