package com.example.areyousmarterthanyourself

import android.app.Application

class AreYouSmarterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        GameScoreManager.initialize(this)
    }
}