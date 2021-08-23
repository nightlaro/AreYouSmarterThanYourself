package com.example.areyousmarterthanyourself

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val SCORE_HISTORY = "GAME_SCORE_HISTORY"

/**
 * A simple [Fragment] subclass.
 * Use the [HighScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HighScoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(SCORE_HISTORY)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_high_score, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            HighScoreFragment().apply {
                arguments = Bundle().apply {
                    putString(SCORE_HISTORY, param1)
                }
            }
    }
}