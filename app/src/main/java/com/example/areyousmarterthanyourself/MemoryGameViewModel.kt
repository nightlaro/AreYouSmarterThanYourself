package com.example.areyousmarterthanyourself

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

class MemoryGameViewModel() : ViewModel() {
    val pairStorage = mutableListOf<ValidCards>()
    val cards : MutableLiveData<List<CardData>> by lazy {
        MutableLiveData<List<CardData>>(loadCards())
    }

    fun isPair(): Boolean {
        val firstCardId = pairStorage.first()
        val lastCardId = pairStorage.last()
        return firstCardId == lastCardId
    }

    fun pushCard(cardId: ValidCards) {
        pairStorage.add(cardId)
    }

    fun resetCardSet() {
        pairStorage.clear()
    }

    fun loadCards(): List<CardData> {
        val listOfIcons = listOf<CardData>(
            CardData(R.drawable.ic__01_youtube, null, ValidCards.YOUTUBE, false),
            CardData(R.drawable.ic__02_yelp, null, ValidCards.YELP, false),
            CardData(R.drawable.ic__03_wordpress, null, ValidCards.WORDPRESS, false),
            CardData(R.drawable.ic__04_wikipedia, null, ValidCards.WIKIPEDIA, false),
            CardData(R.drawable.ic__05_whatsapp, null, ValidCards.WHATSAPP, false),
            CardData(R.drawable.ic__06_vine, null, ValidCards.VINE, false),
            CardData(R.drawable.ic__07_vimeo, null, ValidCards.VIMEO, false),
            CardData(R.drawable.ic__08_twitter, null, ValidCards.TWITTER, false)
        )

        return (listOfIcons + listOfIcons).shuffled()
    }

    fun getCards() : LiveData<List<CardData>> {
        return cards
    }

    fun updateCards(updatedCards : List<CardData>) {
        cards.postValue(updatedCards)
    }
}