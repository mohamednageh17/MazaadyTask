plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.mazaadytask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mazaadytask"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://staging.mazaady.com/api/v1/\"")
            buildConfigField("String", "PRIVATE_KEY", "\"3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?\$@+16\"")
        }

        release {

            buildConfigField("String", "BASE_URL", "\"https://staging.mazaady.com/api/v1/\"")
            buildConfigField("String", "PRIVATE_KEY", "\"3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?\$@+16\"")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packaging {
        resources {
            // Exclude the problematic META-INF/gradle/incremental.annotation.processors path
            resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(
        project.project(":data")
    )

    implementation(
        project.project(":domain")
    )
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)



//hilt
    implementation(libs.dagger.hilt.core)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.fragment)
    implementation(libs.hilt.compiler)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //room
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    //room
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    kapt("androidx.room:room-compiler:2.6.1")

    //chucker
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)
    implementation (libs.circleimageview)

    // JUnit
    testImplementation ("junit:junit:4.13.2")

    // Mockk for mocking
    testImplementation ("io.mockk:mockk:1.12.0")

    // Kotlin Coroutines Test
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Truth or AssertJ for assertions
    testImplementation ("com.google.truth:truth:1.1.3")

    // If you're using Mockito instead of Mockk
    testImplementation ("org.mockito:mockito-core:3.9.0")

}