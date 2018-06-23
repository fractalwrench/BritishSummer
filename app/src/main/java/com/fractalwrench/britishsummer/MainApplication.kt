package com.fractalwrench.britishsummer

import android.app.Application
import androidModule
import com.bugsnag.android.Bugsnag
import dataModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val client = Bugsnag.init(this)
        client.config.notifyReleaseStages = arrayOf("production")

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val baseUrl = getString(R.string.weather_api_url)
        val apiKey = getString(R.string.weather_api_key)
        val networkModule = generateNetworkModule(baseUrl, apiKey)
        startKoin(this, listOf(androidModule, dataModule, networkModule, viewModelModule))
    }
}