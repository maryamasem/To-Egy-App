import org.gradle.kotlin.dsl.androidTestImplementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.depi.toegy"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.depi.toegy"
        minSdk = 29
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.ads.mobile.sdk)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.ui.test.junit4)
    testImplementation(libs.junit)
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation ("androidx.navigation:navigation-testing:2.8.0")
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.navigation:navigation-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("androidx.navigation:navigation-compose:2.9.4")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.7")


    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    //Firebase Auth
    implementation("com.google.firebase:firebase-auth")
    //FireStore
    implementation("com.google.firebase:firebase-firestore")

    implementation("com.google.maps.android:maps-compose:4.3.3")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
}