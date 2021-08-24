package com.example.areyousmarterthanyourself

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

class MemoryGameAdapter(cardsData : List<CardData>,
                        private val clickListener: CardOnClick)
    : RecyclerView.Adapter<MemoryGameAdapter.CardViewHolder>() {

     class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemCard : CardView = view.findViewById(R.id.item_card)
    }

    interface CardOnClick {
        fun cardOnClick(cardID: ValidCards, position : Int, view : View)
    }

    var cards = cardsData
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_view_holder, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val backgroundImageResId = cards[position].resId
        if (!cards[position].matched) {
            holder.itemCard.setBackgroundColor(Color.parseColor("black"))
            holder.itemCard.setOnClickListener { view ->
                view.background = ResourcesCompat.getDrawable(holder.itemCard.context.resources,
                    backgroundImageResId,
                    null)
                view.postInvalidate()
                clickListener.cardOnClick(cards[position].id, position, view)
            }
        }
    }

    override fun getItemCount() = cards.size
}