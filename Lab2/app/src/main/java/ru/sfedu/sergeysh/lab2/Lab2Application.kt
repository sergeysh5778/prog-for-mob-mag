package ru.sfedu.sergeysh.lab2

import android.app.Application
import com.google.android.material.color.DynamicColors

class Lab2Application : Application() {

    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
