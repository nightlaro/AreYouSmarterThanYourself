package com.example.areyousmarterthanyourself

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class HighScoreFragmentAdapter(scoreHistory : List<String>)
    : RecyclerView.Adapter<HighScoreFragmentAdapter.ScoreListingViewHolder>() {

    var scoreHistory = scoreHistory
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ScoreListingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val scoreItem = view.findViewById<TextView>(R.id.item_score)
        val scoreListNumbering = view.findViewById<TextView>(R.id.item_score_list_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreListingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_score_view_holder, parent, false)
        return ScoreListingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreListingViewHolder, position: Int) {
        holder.scoreItem.text = scoreHistory[position]
        holder.scoreListNumbering.text = "${(position + 1)}."
    }

    override fun getItemCount() = scoreHistory.size

}