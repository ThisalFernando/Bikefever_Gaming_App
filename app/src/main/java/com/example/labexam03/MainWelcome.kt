package com.example.labexam03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainWelcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            startActivity(Intent(this, MainStart::class.java))
            finish()
        },3000)
    }
}