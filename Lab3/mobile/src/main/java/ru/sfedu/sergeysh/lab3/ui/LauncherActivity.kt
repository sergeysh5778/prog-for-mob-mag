package ru.sfedu.sergeysh.lab3.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ru.sfedu.sergeysh.lab3.databinding.ActivityLauncherBinding
import ru.sfedu.sergeysh.lab3.ui.login.LoginActivity

class LauncherActivity : AppCompatActivity() {

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
