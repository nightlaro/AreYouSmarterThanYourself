package com.example.areyousmarterthanyourself

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import java.util.*

enum class ValidCards {
    YOUTUBE,
    YELP,
    WORDPRESS,
    WIKIPEDIA,
    WHATSAPP,
    VINE,
    VIMEO,
    TWITTER
}

class MemoryGameViewModel(app: Application) : AndroidViewModel(app) {
    var pairStorage = Pair<CardData?, CardData?>(null, null)

    private val scoreManager = GameScoreManager(app)

    private val cards : MutableLiveData<List<CardData>> by lazy {
        MutableLiveData<List<CardData>>(loadCards())
    }

    private val score : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(loadScore())
    }

    private val cardsValue : MutableList<CardData>
        get() = cards.value!!.toMutableList() //the !! looks iffy but I figured that everytime cards gets invoked,
    // we generate the card anyways and loadCards wont delay

    private fun loadScore(): Int {
        return scoreManager.getScore()
    }

    fun getScore() : LiveData<Int> {
        return score
    }

    fun updateScore(x : Int) {
        scoreManager.saveScore(x)
        score.value = x
    }

    fun resetScore() {
        scoreManager.resetScore()
        score.value = 0
    }

    fun saveScoreToHistory() {
        scoreManager.saveScoreHistory()
    }

    fun resetCards() {
        updateCards(loadCards())
    }

    fun setIsTapped(pos: Int): CardData {
        val cardsTempCopy = cardsValue
        cardsTempCopy[pos]= cardsTempCopy[pos].copy(isTapped = true)
        updateCards(cardsTempCopy)
        return cardsTempCopy[pos]
    }

    fun resetIsTapped() {
        val cardsTempCopy = cardsValue
        for (card in cardsTempCopy) {
            if (!card.matched && card.isTapped) {
                cardsTempCopy[cardsTempCopy.indexOfFirst { it.Id == card.Id }] = card.copy(isTapped = false)
            }
        }
        updateCards(cardsTempCopy)
        Log.d("ResetIsTapped", "Card ${cards.value}")
    }

    fun setCardsMatched(firstCard : CardData, secondCard : CardData) {
        val cardsTempCopy = cardsValue
        val firstPos = cardsTempCopy.indexOfFirst { it.Id == firstCard.Id }
        val secondPos = cardsTempCopy.indexOfFirst { it.Id == secondCard.Id }
        cardsTempCopy[firstPos] = cardsTempCopy[firstPos].copy(matched = true)
        cardsTempCopy[secondPos] = cardsTempCopy[secondPos].copy(matched = true)
        updateCards(cardsTempCopy)
        resetIsTapped()
    }

    fun isMatch(): Boolean {
        Log.d("PairCheck", "Checking Pair..")
        if (pairStorage.first == null || pairStorage.second == null) return false
        val firstCardId = pairStorage.first!!.cardType
        val lastCardId = pairStorage.second!!.cardType
        Log.d("PairCheck", "Pair? : ${firstCardId == lastCardId}")
        return firstCardId == lastCardId
    }

    fun pushCard(card: CardData) {
        Log.d("GameVM", "Current storage $pairStorage")
        if (pairStorage.first != null && pairStorage.second != null) {
            throw IllegalArgumentException("Pair storage is full! lol")
        }
        if (pairStorage.first == null) {
            Log.d("GameVM", "Pushing first card $card")
            pairStorage = pairStorage.copy(first = card)
            return
        }
        if (pairStorage.second == null) {
            Log.d("GameVM", "Pushing second card $card")
            pairStorage = pairStorage.copy(second = card)
            return
        }
    }

    fun resetCardPair() {
        pairStorage = pairStorage.copy(null, null)
    }

    fun loadCards(): List<CardData> {
        val listOfIcons = listOf(
            CardData(UUID.randomUUID() ,R.drawable.ic__01_youtube, null, ValidCards.YOUTUBE,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__02_yelp, null, ValidCards.YELP,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__03_wordpress, null, ValidCards.WORDPRESS,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__04_wikipedia, null, ValidCards.WIKIPEDIA,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__05_whatsapp, null, ValidCards.WHATSAPP,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__06_vine, null, ValidCards.VINE,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__07_vimeo, null, ValidCards.VIMEO,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__08_twitter, null, ValidCards.TWITTER,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__01_youtube, null, ValidCards.YOUTUBE,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__02_yelp, null, ValidCards.YELP,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__03_wordpress, null, ValidCards.WORDPRESS,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__04_wikipedia, null, ValidCards.WIKIPEDIA,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__05_whatsapp, null, ValidCards.WHATSAPP,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__06_vine, null, ValidCards.VINE,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__07_vimeo, null, ValidCards.VIMEO,
                false, false),
            CardData(UUID.randomUUID() ,R.drawable.ic__08_twitter, null, ValidCards.TWITTER,
                false, false),
        )

        return listOfIcons.shuffled()
    }

    fun getCards() : LiveData<List<CardData>> {
        return cards
    }

    fun updateCards(updatedCards : List<CardData>) {
        Log.d("UPDATINGCARD", "Updating card..")
        cards.value = updatedCards
        Log.d("UPDATINGCARD", "New LiveData ${cards.value}")
    }
}