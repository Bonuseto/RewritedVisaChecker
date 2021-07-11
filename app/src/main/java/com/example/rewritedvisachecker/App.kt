package com.example.rewritedvisachecker

import android.app.Application
import com.example.rewritedvisachecker.data.PreferenceManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        preferenceManager = PreferenceManager(this)
    }

    companion object {
        @JvmField
        var preferenceManager: PreferenceManager? = null
    }
}