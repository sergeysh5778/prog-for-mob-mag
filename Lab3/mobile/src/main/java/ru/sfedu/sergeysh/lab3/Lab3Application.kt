package ru.sfedu.sergeysh.lab3

import android.app.Application
import com.google.android.material.color.DynamicColors

class Lab3Application : Application() {

    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
