package com.example.areyousmarterthanyourself

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MemoryGameActivity : AppCompatActivity(), MemoryGameAdapter.CardOnClick {

    companion object {
        fun launchMemoryGameActivity(context: Context) {
            val intent = Intent(context, MemoryGameActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val model : MemoryGameViewModel by viewModels()
    val scoreManager = GameScoreManager(this)
    var score = 0
    lateinit var cardsHolder : MutableList<CardData>
    lateinit var scoreTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        val mainActivityRecyclerView = findViewById<RecyclerView>(R.id.cards_recyclerView)
        val mainActivityAdapter = MemoryGameAdapter(listOf<CardData>(), this)
        mainActivityRecyclerView.layoutManager = GridLayoutManager(this, 4)
        mainActivityRecyclerView.adapter = mainActivityAdapter

        score = scoreManager.getScore()
        scoreTextView = findViewById(R.id.score)
        scoreTextView.text = "SCORE: ${score.toString()}"

        model.getCards().observe(this, Observer<List<CardData>> { cards ->
            cardsHolder = cards.toMutableList()
            mainActivityAdapter.cards = cards
        })
    }

    override fun onPause() {
        super.onPause()
        model.updateCards(cardsHolder)
        score = scoreManager.getScore()
    }

    data class CardTapped(val view: View, val pos : Int)
    private val viewStorage = mutableListOf<CardTapped>()

    private fun resetStorages() {
        viewStorage.clear()
        model.resetCardSet()
    }

    private fun resetCardBackground() {
        viewStorage.forEach {
            Log.d("TAPPED", "ACTUALLY resetting background ${Date().time}")
            it.view.setBackgroundColor(Color.parseColor("black"))
        }
    }

    private fun removeCardView() {
        viewStorage.forEach { cardTapped ->
            cardTapped.view.parent?.let {
                (it as ViewGroup).removeView(cardTapped.view)
            }
        }
    }

    private fun isSameCardClicked(): Boolean {
        if (viewStorage.isEmpty()) return false
        val firstHashCode = viewStorage.first().hashCode()
        val secondHashCode = viewStorage.last().hashCode()
        if (firstHashCode == secondHashCode) {
            return true
        }
        return false
    }

    private fun invokeResetsWithDelay(delayInMillis: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
                                                    resetCardBackground()
                                                    resetStorages()
                                                    }, delayInMillis)
    }

    fun updateScore() {
        score += 1
        scoreTextView.text = "SCORE: ${score.toString()}"
        scoreManager.saveScore(score)
    }

    fun isGameOver(): Boolean {
        for (cards in cardsHolder) {
            if (!cards.matched) {
                return false
            }
        }
        return true
    }

    fun resetGame() {
        val newCards = model.loadCards()
        model.updateCards(newCards)
    }

    override fun cardOnClick(cardID : ValidCards, position: Int, view: View) {
        viewStorage.add(CardTapped(view, position))
        model.pushCard(cardID)
        if (model.pairStorage.size > 1) {
            if (!isSameCardClicked()) {
                if (model.isPair()) {
                    cardsHolder[viewStorage[0].pos].matched = true
                    cardsHolder[viewStorage[1].pos].matched = true
                    updateScore()
                    removeCardView()
                    resetStorages()
                }
                if (isGameOver()) {
                    scoreManager.saveScoreHistory()
                    scoreManager.resetScore()
                    resetGame()
                }
            }
            invokeResetsWithDelay(1000)
        }
    }

}