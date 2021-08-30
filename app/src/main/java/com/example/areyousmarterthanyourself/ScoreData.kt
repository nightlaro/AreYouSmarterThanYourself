package com.example.areyousmarterthanyourself

import kotlinx.serialization.Serializable

@Serializable
data class ScoreData(val score : Int, val scoreHistory : List<String>)