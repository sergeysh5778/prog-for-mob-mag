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
    implementation("androidx.core:core-ktx:1.8.0-beta01")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.wear:wear:1.2.0")
    implementation("com.google.android.gms:play-services-wearable:17.1.0")
}
