package com.fractalwrench.britishsummer

import android.app.Application
import com.bugsnag.android.Bugsnag

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Bugsnag.init(this)
        Bugsnag.notify(RuntimeException())
    }

}