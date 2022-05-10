package ru.sfedu.sergeysh.lab3

import android.app.Activity
import android.os.Bundle
import ru.sfedu.sergeysh.lab3.databinding.ActivityMainBinding

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
