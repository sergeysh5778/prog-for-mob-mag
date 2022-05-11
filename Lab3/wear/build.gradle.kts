plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.sfedu.sergeysh.lab3"
    compileSdkPreview = "Tiramisu"
    buildToolsVersion = "33.0.0 rc3"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    defaultConfig {
        applicationId = "ru.sfedu.sergeysh.lab3"
        versionCode = 1
        versionName = "1.0"
        targetSdk = 30
        minSdk = 30
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":common")))
    wearApp(project(":wear"))
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.core:core-ktx:1.8.0-beta01")
    implementation("androidx.fragment:fragment-ktx:1.5.0-beta01")
    implementation("androidx.wear:wear:1.2.0")
    implementation("com.google.android.gms:play-services-wearable:17.1.0")
    implementation("com.google.android.material:material:1.6.0")
}
