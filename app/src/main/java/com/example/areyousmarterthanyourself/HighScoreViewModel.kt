package com.example.areyousmarterthanyourself

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

class HighScoreViewModel(app: Application) : AndroidViewModel(app) {

    private val scoreManager = GameScoreManager(app)

    private val score : Int
        get() {
            return scoreManager.getScore()
        }
    private val scoreHistory : List<String>
        get() {
            return scoreManager.getScoreHistory()
        }

    private val scoreLiveData : MutableLiveData<ScoreData> by lazy {
        MutableLiveData<ScoreData>(ScoreData(score, scoreHistory))
    }

    fun getScoreLiveData(): LiveData<ScoreData> {
        return scoreLiveData
    }

}