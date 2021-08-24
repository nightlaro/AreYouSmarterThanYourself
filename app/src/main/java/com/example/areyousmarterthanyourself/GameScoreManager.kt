package com.example.areyousmarterthanyourself

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class GameScoreManager(val context : Context) {

    private val sharedPref : SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private var historyScore = listOf<String>()

    fun saveScore(score: Int) {
        sharedPref.edit {
            putInt("SCORE", score)
        }
    }

    fun getScore(): Int {
        return sharedPref.getInt("SCORE", 0)
    }

    fun saveScoreHistory() {
        getScoreHistory()?.let {
            historyScore = it
        }
        val historyScoreSet = historyScore.toMutableSet()
        historyScoreSet.add(getScore().toString())
        sharedPref.edit {
            putStringSet("SCORE_HISTORY", historyScoreSet)
        }
    }

    fun getScoreHistory(): List<String>? {
        val savedHistory = sharedPref.getStringSet("SCORE_HISTORY", historyScore.toMutableSet())
        return savedHistory?.toList()
    }

    fun resetScore() {
        saveScore(0)
    }
}