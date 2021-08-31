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

    val scores : MutableLiveData<List<String>> by lazy {
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
            scores.value = it
        }
        val historyScoreSet = scores.value!!.toMutableSet()
        historyScoreSet.add(getScore().toString())
        sharedPref.edit {
            putStringSet("SCORE_HISTORY", historyScoreSet)
        }
        scores.value = historyScoreSet.toList()
    }

    fun getScoreHistory(): List<String> {
        val savedHistory = sharedPref.getStringSet("SCORE_HISTORY", scores.value!!.toMutableSet())
        return savedHistory?.toList() ?: emptyList()
    }

    fun resetScore() {
        saveScore(0)
    }
}