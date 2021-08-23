package com.example.areyousmarterthanyourself

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit

class HighScoreActivity : AppCompatActivity() {

    companion object {
        fun launchHighScoreActivity(context: Context) {
            val intent = Intent(context, HighScoreActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        val scoreManager = GameScoreManager(this)
        val listGameScore = scoreManager.getScoreHistory()
        val gameScoreHistory = arrayListOf<String>()

        listGameScore?.forEach {
            gameScoreHistory.add(it)
        }

        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putStringArrayList("GAME_SCORE_HISTORY", gameScoreHistory)
            supportFragmentManager.commit {
                add<HighScoreFragment>(R.id.high_score_fragment_container, args = bundle)
            }
        }

    }

}