package com.fractalwrench.britishsummer

import android.app.Application
import com.bugsnag.android.Bugsnag
import timber.log.Timber

class MainApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Bugsnag.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val baseUrl = getString(R.string.weather_api_url)
        val apiKey = getString(R.string.weather_api_key)

        appComponent = DaggerAppComponent.builder()
                .androidModule(AndroidModule(this))
                .networkModule(NetworkModule(baseUrl, apiKey))
                .build()
    }
}