package com.example.areyousmarterthanyourself

import android.graphics.drawable.Drawable

data class CardData(
    val resId: Int,
    var backgroundImage: Drawable?,
    val id: VALID_CARDS,
    var matched: Boolean)