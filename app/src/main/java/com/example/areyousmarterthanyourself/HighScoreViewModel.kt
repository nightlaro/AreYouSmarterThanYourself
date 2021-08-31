package com.example.areyousmarterthanyourself

import android.app.Application
import android.util.Log
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
        Log.d("HighScoreViewModel", "Initializing ScoreLiveData \n" +
                "Score: $score \n" +
                "History: $scoreHistory")
        MutableLiveData<ScoreData>(ScoreData(score, scoreHistory))
    }

    init {
        scoreManager.scores.observeForever {
            Log.d("HighScoreViewModel", "onInit Observing score data from manager \n" +
                    "History - $it")
            scoreLiveData.value = ScoreData(score, it)
        }
    }

    fun getScoreLiveData(): LiveData<ScoreData> {
        return scoreLiveData
    }

    override fun onCleared() {
        super.onCleared()
        //remove observers
    }

}