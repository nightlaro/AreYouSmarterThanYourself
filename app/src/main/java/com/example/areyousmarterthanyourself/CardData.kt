package com.example.areyousmarterthanyourself

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

data class CardData(
    val resId: DrawableRes,
    var backgroundImage: Drawable?,
    val id: ValidCards,
    var matched: Boolean)
