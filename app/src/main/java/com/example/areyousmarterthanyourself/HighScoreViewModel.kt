package com.example.areyousmarterthanyourself

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HighScoreViewModel(app: Application) : AndroidViewModel(app) {
    //on init do the observer
    //onclear stop the observer or clean ups
    private val scoreManager = GameScoreManager.instance

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