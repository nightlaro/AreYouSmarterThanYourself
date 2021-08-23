package com.example.areyousmarterthanyourself

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newGameButton = findViewById<Button>(R.id.new_game_button)
        val highScoreButton = findViewById<Button>(R.id.high_score_button)
        newGameButton.setOnClickListener {
            MemoryGameActivity.launchMemoryGameActivity(this)
        }
        highScoreButton.setOnClickListener {
            HighScoreActivity.launchHighScoreActivity(this)
        }
    }
}