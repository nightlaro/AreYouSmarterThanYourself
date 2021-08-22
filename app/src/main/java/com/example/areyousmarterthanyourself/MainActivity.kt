package com.example.areyousmarterthanyourself

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity(), MainActivityAdapter.CardOnClick {

    private val model : MainActivityViewModel by viewModels()
    lateinit var cardsHolder : MutableList<CardData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainActivityRecyclerView = findViewById<RecyclerView>(R.id.cards_recyclerView)
        val mainActivityAdapter = MainActivityAdapter(listOf<CardData>(), this)
        mainActivityRecyclerView.layoutManager = GridLayoutManager(this, 4)
        mainActivityRecyclerView.adapter = mainActivityAdapter
        model.getCards().observe(this, Observer<List<CardData>> { cards ->
            Log.d("MODEL", "Observer Cards: $cards")
            cardsHolder = cards.toMutableList()
            mainActivityAdapter.cards = cards
        })
    }

    override fun onPause() {
        super.onPause()
        model.updateCards(cardsHolder)
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

    override fun cardOnClick(cardId : VALID_CARDS,position: Int, view: View) {
        viewStorage.add(CardTapped(view, position))
        model.pushCard(cardId)
        if (model.pairStorage.size > 1) {
            if (!isSameCardClicked()) {
                if (model.isPair()) {
                    cardsHolder[viewStorage[0].pos].matched = true
                    cardsHolder[viewStorage[1].pos].matched = true
                    removeCardView()
                    resetStorages()
                }
            }
            invokeResetsWithDelay(1000)
        }
    }

}