package com.fractalwrench.britishsummer

import android.app.Application
import androidModule
import com.bugsnag.android.Bugsnag
import com.fractalwrench.britishsummer.log.generateLogModule
import dataModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val client = Bugsnag.init(this)
        client.config.notifyReleaseStages = arrayOf("production")

        val baseUrl = getString(R.string.weather_api_url)
        val apiKey = getString(R.string.weather_api_key)
        val networkModule = generateNetworkModule(baseUrl, apiKey)
        val logModule = generateLogModule(client)
        val modules = listOf(androidModule, dataModule, networkModule, viewModelModule, logModule)
        startKoin(this, modules)
    }
}