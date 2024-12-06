plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization.json)
}

android {
    namespace = "com.example.flickster"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.flickster"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true // Enable BuildConfig generation
    }

    buildTypes {
        debug {
            val tmdbApiKey = project.findProperty("TMDB_API_KEY") ?: System.getenv("TMDB_API_KEY")
            ?: throw IllegalArgumentException("TMDB_API_KEY is not defined")
            buildConfigField("String", "API_KEY", "\"$tmdbApiKey\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            val tmdbApiKey = project.findProperty("TMDB_API_KEY") ?: System.getenv("TMDB_API_KEY")
            ?: throw IllegalArgumentException("TMDB_API_KEY is not defined")
            buildConfigField("String", "API_KEY", "\"$tmdbApiKey\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.gson)
    implementation(libs.glide) // Image loading library
    implementation(libs.asynchttpclient) // HTTP client
    implementation(libs.kotlinx.serialization.json) // JSON serialization
    annotationProcessor(libs.glide.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}