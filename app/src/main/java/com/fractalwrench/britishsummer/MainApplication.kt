package com.fractalwrench.britishsummer

import android.app.Application
import com.bugsnag.android.Bugsnag

class MainApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Bugsnag.init(this)

        appComponent = DaggerAppComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
    }
}