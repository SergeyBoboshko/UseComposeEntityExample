plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp") version "2.0.0-1.0.24"
    id("kotlin-parcelize")

}

android {
    namespace = "io.github.sergeyboboshko.usecomposeentityexample"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.sergeyboboshko.usecomposeentityexample"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    ksp {
        arg("skipTests", "true")
    }
}

tasks.withType<Test> {
    enabled = false
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
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    implementation (project(":composeentity_ksp"))
    ksp(project(":composeentity_ksp"))

    //***************************** ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ***********************************
    implementation(project(":app"))//Be shure co comment this string and uncomment and set up the newest version
    //Warning! In your projects change implementation(project(":app")) to
    //implementation("io.github.sergeyboboshko:composeentity:x.x.x") where x.x.x is last version of the Compose Entity Library
    //The last version you could find on the Maven https://central.sonatype.com/ or oficial Compose Entity blog http://www.homeclub.top/?p=1036
    //implementation("io.github.sergeyboboshko:composeentity:1.0.6")//Consider to uncomment this string and set the actual version
    //***************************** ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ***********************************


    implementation("androidx.navigation:navigation-compose:2.8.3")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Необходимо включить кодогенерацию для kapt
kapt {
    correctErrorTypes = true
    useBuildCache = true
}

// Налаштування для KSP
ksp {
    arg("ksp.allow.all.target.configuration", "false")  // Це забезпечить застосування конфігурації лише для основного source set
}