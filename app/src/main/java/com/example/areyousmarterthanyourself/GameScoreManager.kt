package com.example.areyousmarterthanyourself

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

class GameScoreManager(val context : Context) {

    companion object {
        lateinit var instance : GameScoreManager
        private var _instance : GameScoreManager? = null
        fun initialize(app: Application) {
            if (_instance == null) {
              _instance = GameScoreManager(app)
                instance = _instance!! //we know for a fact we set this to GSM ^ above line
            }
        }
    }

    private val sharedPref : SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    val historyScore : MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(listOf())
    }

    fun saveScore(score: Int) {
        sharedPref.edit {
            putInt("SCORE", score)
        }
    }

    fun getScore(): Int {
        return sharedPref.getInt("SCORE", 0)
    }

    fun saveScoreHistory() {
        getScoreHistory().let {
            historyScore.value = it
        }
        val historyScoreSet = historyScore.value!!.toMutableSet()
        historyScoreSet.add(getScore().toString())
        sharedPref.edit {
            putStringSet("SCORE_HISTORY", historyScoreSet)
        }
        historyScore.value = historyScoreSet.toList()
    }

    fun getScoreHistory(): List<String> {
        val savedHistory = sharedPref.getStringSet("SCORE_HISTORY", historyScore.value!!.toMutableSet())
        return savedHistory?.toList() ?: emptyList()
    }

    fun resetScore() {
        saveScore(0)
    }
}