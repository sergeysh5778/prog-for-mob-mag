plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}
val nav_version by extra("2.5.0-beta01")

android {
    namespace = "ru.sfedu.sergeysh.lab2"
    compileSdkPreview = "Tiramisu"

    defaultConfig {
        applicationId = "ru.sfedu.sergeysh.lab2"
        minSdk = 32
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        viewBinding = true
    }
    buildToolsVersion = "33.0.0 rc3"
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0-beta01")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0-rc01")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0-beta01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-beta01")
    implementation("androidx.annotation:annotation:1.3.0")
    implementation("com.github.bumptech.glide:glide:4.13.2")
    implementation("org.jsoup:jsoup:1.14.3")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
