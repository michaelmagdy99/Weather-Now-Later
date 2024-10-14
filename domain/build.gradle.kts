plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.domain"
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

    implementation(project(":core"))

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
    // JUnit for testing
    testImplementation (libs.junit)
    // Mockito for mocking
    testImplementation (libs.mockito.core.v451)
    // Mockito Kotlin extension for easier usage
    testImplementation (libs.mockito.kotlin)
    // Coroutine test support
    testImplementation (libs.kotlinx.coroutines.test.v160)

}