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
        targetSdk = 32
        minSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    wearApp(project(":wear"))
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.core:core-ktx:1.8.0-beta01")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.0-beta01")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.0-beta01")
    implementation("com.google.android.gms:play-services-wearable:17.1.0")
    implementation("com.google.android.material:material:1.6.0-rc01")
    implementation("com.github.bumptech.glide:glide:4.13.2")
    implementation("org.jsoup:jsoup:1.14.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
