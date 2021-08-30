package com.example.areyousmarterthanyourself

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
//
class MemoryGameActivity : AppCompatActivity(), MemoryGameAdapter.CardOnClick {

    companion object {
        fun launchMemoryGameActivity(context: Context) {
            val intent = Intent(context, MemoryGameActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val model : MemoryGameViewModel by viewModels()
//        get() {
//            //TODO look up how to create a viemodel
//        }
    private val scoreManager = GameScoreManager(this)

    private lateinit var cardsHolder : List<CardData>
    private lateinit var scoreTextView : TextView
    private lateinit var memoryGameAdapter : MemoryGameAdapter
    private val tempCardHolder = mutableListOf<CardData>()
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        val mainActivityRecyclerView = findViewById<RecyclerView>(R.id.cards_recyclerView)
        memoryGameAdapter = MemoryGameAdapter(listOf(), this)
        mainActivityRecyclerView.layoutManager = GridLayoutManager(this, 4)
        mainActivityRecyclerView.adapter = memoryGameAdapter

        scoreTextView = findViewById(R.id.score)
        scoreTextView.text = "SCORE: $score"

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<HighScoreFragment>(R.id.high_score_fragment_container)
            }
        }

        model.getScore().observe(this) { scoreData ->
            Log.d("test", "Getting score")
            score = scoreData
            scoreTextView.text = "SCORE: $scoreData"
        }

        model.getCards().observe(this) { cards ->
            Log.d("test", "Getting cards")
            cardsHolder = cards
            memoryGameAdapter.cards = cards
        }
    }

    override fun onPause() {
        super.onPause()
        model.updateCards(cardsHolder)
        score = scoreManager.getScore()
    }

    override fun onDestroy() {
        super.onDestroy()
        scoreManager.saveScoreHistory()
    }

    private fun resetStorages() {
        model.resetCardPair()
    }

    private fun isSameCardClicked(): Boolean {
        if (tempCardHolder.isEmpty()) return false
        if (tempCardHolder.size < 2) return false
        if (tempCardHolder.first().Id == tempCardHolder.last().Id) {
            tempCardHolder.removeLast()
            return true
        }
        return false
    }

    private fun invokeResetsWithDelay(delayInMillis: Long) {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                Log.d("RESET", "****RESETTING****")
                tempCardHolder.clear()
                model.resetIsTapped()
                resetStorages()
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }, delayInMillis)
    }

    private fun updateScore() {
        val addedScore = score + 1
        model.updateScore(addedScore)
    }

    private fun isGameOver(): Boolean {
        for (cards in cardsHolder) {
            if (!cards.matched) {
                return false
            }
        }
        return true
    }

    private fun resetGame() {
        model.saveScoreToHistory()
        model.resetScore()
        model.resetCards()
    }

    override fun cardOnClick(card : CardData, position: Int) {
        tempCardHolder.add(card)
        if(!isSameCardClicked()) {
            val cardThatIsTapped = model.setIsTapped(position)
            model.pushCard(cardThatIsTapped)
        }
        if (model.pairStorage.second != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            if (!isSameCardClicked()) {
                if (model.isMatch()) {
                    val (first, second) = model.pairStorage
                    //isPair checks if pairStorage has a null, returns true if both are not null
                    model.setCardsMatched(first!!, second!!)
                    updateScore()
                    resetStorages()
                }
            }
            invokeResetsWithDelay(1000)
        }
        if (isGameOver()) {
            resetGame()
        }
    }

}