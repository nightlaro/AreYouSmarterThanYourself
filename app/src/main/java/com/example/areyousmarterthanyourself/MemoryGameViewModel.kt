package com.example.areyousmarterthanyourself

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import kotlinx.coroutines.launch
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

    fun updateScore(newScore : Int) {
        scoreManager.saveScore(newScore)
        score.value = newScore
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
        val cardsTempCopy = cards.value!!.toMutableList()
        for (card in cardsTempCopy) {
            if (!card.matched && card.isTapped) {
                cardsTempCopy[cardsTempCopy.indexOfFirst { it.Id == card.Id }] = card.copy(isTapped = false)
            }
        }
        updateCards(cardsTempCopy)
    }

    fun setCardsMatched(firstCard : CardData, secondCard : CardData) {
        val cardsTempCopy = cardsValue
        val firstPos = cardsTempCopy.indexOfFirst { it.Id == firstCard.Id }
        val secondPos = cardsTempCopy.indexOfFirst { it.Id == secondCard.Id }
        cardsTempCopy[firstPos] = cardsTempCopy[firstPos].copy(matched = true)
        cardsTempCopy[secondPos] = cardsTempCopy[secondPos].copy(matched = true)
        updateCards(cardsTempCopy)
        viewModelScope.launch {
            resetIsTapped()
        }
    }

    fun isMatch(): Boolean {
        if (pairStorage.first == null || pairStorage.second == null) return false
        val firstCardId = pairStorage.first!!.cardType
        val lastCardId = pairStorage.second!!.cardType
        return firstCardId == lastCardId
    }

    fun pushCard(card: CardData) {
        if (pairStorage.first != null && pairStorage.second != null) {
            throw IllegalArgumentException("Pair storage is full! lol")
        }
        if (pairStorage.first == null) {
            pairStorage = pairStorage.copy(first = card)
            return
        }
        if (pairStorage.second == null) {
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
        cards.value = updatedCards
    }
}