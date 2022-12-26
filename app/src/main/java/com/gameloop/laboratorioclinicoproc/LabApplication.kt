package com.gameloop.laboratorioclinicoproc

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class LabApplication : Application() {

    private val appScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        appScope.launch {
            initialize()
        }
    }

    private fun initialize() {
        initializeTimber()
    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }
}