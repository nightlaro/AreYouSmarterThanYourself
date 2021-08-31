package com.example.areyousmarterthanyourself

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
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

    private val TAG = "GameScoreManager"

    private val sharedPref : SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    val scores : MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(listOf())
    }

    fun getScoresLiveData() : LiveData<List<String>> {
        return scores
    }

    private fun updateScoresLiveData(newScoresList : List<String>) {
        Log.d(TAG, "Saving...: $newScoresList")
        scores.value = newScoresList
        Log.d(TAG, "New LiveData: ${scores.value}")
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
        val historyScoreSet = scores.value!!.toMutableSet()
        Log.d("GameScoreManager", "Adding score to scores: ${getScore().toString()}")
        historyScoreSet.add(getScore().toString())
        Log.d("GameScoreManager", "Updated scores: $historyScoreSet")
        sharedPref.edit {
            putStringSet("SCORE_HISTORY", historyScoreSet)
            Log.d("GameScoreManager", "Saved file scores: $historyScoreSet")
        }
        updateScoresLiveData(historyScoreSet.toList())
        Log.d("GameScoreManager", "Live data ${scores.value}")
    }

    fun getScoreHistory(): List<String> {
        val savedHistory = sharedPref.getStringSet("SCORE_HISTORY", scores.value!!.toMutableSet())
        return savedHistory?.toList() ?: emptyList()
    }

    fun resetScore() {
        saveScore(0)
    }
}