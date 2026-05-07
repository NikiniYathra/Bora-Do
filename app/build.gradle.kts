// File: app/build.gradle.kts

// ─── PLUGINS ────────────────────────────────────────────────────────────────
// We only apply the Android application and Kotlin Android plugins,
// plus kapt for annotation processing and Safe Args for navigation.
// No version-catalog aliases here, and no Compose plugin.
plugins {
    // Android application support
    id("com.android.application")

    // Kotlin support for Android
    id("org.jetbrains.kotlin.android")

    // Kotlin annotation processor (needed for Room’s @Database, etc.)
    id("kotlin-kapt")

    // Navigation Safe Args (generates FragmentArgs & Directions)
    id("androidx.navigation.safeargs.kotlin")
}

// Tell KAPT to stub unknown annotation types rather than fail
kapt {
    correctErrorTypes = true
}

// ─── ANDROID BLOCK ──────────────────────────────────────────────────────────
android {
    namespace   = "lk.kdu.ac.mc.todolistapp"  // your app’s package
    compileSdk  = 35                          // must be ≥ 26 for adaptive icons

    defaultConfig {
        applicationId = "lk.kdu.ac.mc.todolistapp"
        minSdk        = 21    // API level you support on devices
        targetSdk     = 35    // should match compileSdk
        versionCode   = 1
        versionName   = "1.0"
    }

    // Enable only ViewBinding since we’re using XML layouts
    buildFeatures {
        viewBinding = true
    }

    // Java 8 compatibility
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// ─── DEPENDENCIES ───────────────────────────────────────────────────────────
dependencies {
    // — Core AndroidX & Material —
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    // — Layout & UI widgets —
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.0")

    // — Lifecycle (LiveData, ViewModel) —
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // — Navigation (XML-based) —
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")

    // — Room (Local database) —
    implementation("androidx.room:room-runtime:2.5.0") // runtime
    implementation("androidx.room:room-ktx:2.5.0")     // coroutine + Flow support
    kapt("androidx.room:room-compiler:2.5.0")          // annotation processor

    // Unit testing
    testImplementation("junit:junit:4.13.2")

    // Instrumented Android tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // — (Optional) Firebase Cloud —
    // implementation("com.google.firebase:firebase-firestore-ktx:24.4.1")
}
