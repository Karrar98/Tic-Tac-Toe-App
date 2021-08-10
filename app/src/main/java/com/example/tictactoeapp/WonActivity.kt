package com.example.tictactoeapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoeapp.databinding.ActivityWonBinding

class WonActivity : AppCompatActivity() {

    lateinit var binding: ActivityWonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player = intent.getStringExtra("player")
        if(player == "Tie") binding.textViewWon.text = "TIE"
        else binding.textViewWon.text = "$player WON"

        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom)
        binding.textViewWon.startAnimation(anim)

        Handler().postDelayed({
            startActivity(Intent(this@WonActivity, MainActivity::class.java))
        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
