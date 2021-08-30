package com.example.areyousmarterthanyourself

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HighScoreViewModel(app: Application) : AndroidViewModel(app) {

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

    init {
        scoreManager.historyScore.observeForever {
            scoreLiveData.value = ScoreData(score, it)
        }
    }

    fun getScoreLiveData(): LiveData<ScoreData> {
        return scoreLiveData
    }

    override fun onCleared() {
        super.onCleared()
        //TODO remove observers
    }

}