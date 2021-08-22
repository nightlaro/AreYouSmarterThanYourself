package com.example.areyousmarterthanyourself

import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

enum class VALID_CARDS {
    YOUTUBE,
    YELP,
    WORDPRESS,
    WIKIPEDIA,
    WHATSAPP,
    VINE,
    VIMEO,
    TWITTER
}

class MainActivityViewModel(private val state: SavedStateHandle) : ViewModel() {
    val pairStorage = mutableListOf<VALID_CARDS>()
    val cards : MutableLiveData<List<CardData>> by lazy {
        MutableLiveData<List<CardData>>(loadCards())
    }

    fun isPair(): Boolean {
        val firstCardId = pairStorage.first()
        val lastCardId = pairStorage.last()
        return firstCardId == lastCardId
    }

    fun pushCard(cardId: VALID_CARDS) {
        pairStorage.add(cardId)
    }

    fun resetCardSet() {
        pairStorage.clear()
    }

    fun loadCards(): List<CardData> {
        val listOfIcons = listOf<CardData>(
            CardData(R.drawable.ic__01_youtube, null, VALID_CARDS.YOUTUBE, false),
            CardData(R.drawable.ic__02_yelp, null, VALID_CARDS.YELP, false),
            CardData(R.drawable.ic__03_wordpress, null, VALID_CARDS.WORDPRESS, false),
            CardData(R.drawable.ic__04_wikipedia, null, VALID_CARDS.WIKIPEDIA, false),
            CardData(R.drawable.ic__05_whatsapp, null, VALID_CARDS.WHATSAPP, false),
            CardData(R.drawable.ic__06_vine, null, VALID_CARDS.VINE, false),
            CardData(R.drawable.ic__07_vimeo, null, VALID_CARDS.VIMEO, false),
            CardData(R.drawable.ic__08_twitter, null, VALID_CARDS.TWITTER, false)
        )

        return (listOfIcons + listOfIcons).shuffled()
    }

    fun getCards() : LiveData<List<CardData>> {
        Log.d("MODEL", "Cards : ${cards.value}")
        return cards
    }

    fun updateCards(updatedCards : List<CardData>) {
        cards.postValue(updatedCards)
    }
}