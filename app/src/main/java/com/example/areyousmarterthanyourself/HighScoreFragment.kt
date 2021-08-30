package com.example.areyousmarterthanyourself

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val SCORE_HISTORY = "GAME_SCORE_HISTORY"

class HighScoreFragment : Fragment() {

    private val model : HighScoreViewModel by viewModels()
    private var scoreHistory : List<String> = listOf()
    private lateinit var highScoreAdapter : HighScoreFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            scoreHistory = it.getStringArrayList(SCORE_HISTORY) as ArrayList<String>
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val highScoreRecyclerView = view.findViewById<RecyclerView>(R.id.high_score_recyclerView)
        highScoreAdapter = HighScoreFragmentAdapter(scoreHistory)
        highScoreRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        highScoreRecyclerView.adapter = highScoreAdapter

        model.getScoreLiveData().observe(viewLifecycleOwner) {
            scoreHistory = it.scoreHistory
            highScoreAdapter.scoreHistory = it.scoreHistory
        }
    }

//    override fun onResume() {
//        super.onResume()
//        arguments?.let {
//            scoreHistory = it.getStringArrayList(SCORE_HISTORY) as ArrayList<String>
//        }
//        highScoreAdapter.scoreHistory = scoreHistory
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_high_score, container, false)
    }

}