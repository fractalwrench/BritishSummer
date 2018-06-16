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

        appComponent = DaggerAppComponent.builder()
                .androidModule(AndroidModule(this))
                .networkModule(NetworkModule(getString(R.string.weather_api_url)))
                .build()
    }
}