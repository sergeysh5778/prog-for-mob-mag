// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val nav_version by extra("2.5.0-beta01")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

plugins {
    id("com.android.application") version "7.2.0" apply false
    id("com.android.library") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
