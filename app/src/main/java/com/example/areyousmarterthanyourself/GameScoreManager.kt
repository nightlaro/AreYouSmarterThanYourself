package com.example.areyousmarterthanyourself

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class GameScoreManager(val context : Context) {

    val sharedPref by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    var historyScore = listOf<String>()

    fun saveScore(score: Int) {
        sharedPref.edit {
            putInt("SCORE", score)
        }
    }

    fun getScore(): Int {
        return sharedPref.getInt("SCORE", 0)
    }

    fun saveScoreHistory() {
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