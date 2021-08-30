package com.example.areyousmarterthanyourself

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import java.util.*
//
data class CardData(
    val Id: UUID,
    @DrawableRes
    val resId: Int,
    val backgroundImage: Drawable?,
    val cardType: ValidCards,
    val matched: Boolean,
    val isTapped: Boolean)
