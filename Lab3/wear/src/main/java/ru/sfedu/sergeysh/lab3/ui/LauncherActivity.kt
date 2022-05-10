package ru.sfedu.sergeysh.lab3.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ru.sfedu.sergeysh.lab3.databinding.ActivityLauncherBinding

class LauncherActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLauncherBinding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed( {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }, 1500)
    }
}
