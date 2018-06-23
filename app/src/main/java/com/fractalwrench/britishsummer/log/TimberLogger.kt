package com.fractalwrench.britishsummer.log

import com.fractalwrench.britishsummer.BuildConfig
import timber.log.Timber

class TimberLogger() : Loggable {

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun log(msg: String, throwable: Throwable?, logSeverity: LogSeverity) {
        when (logSeverity) {
            LogSeverity.VERBOSE -> Timber.v(throwable, msg)
            LogSeverity.DEBUG -> Timber.d(throwable, msg)
            LogSeverity.INFO -> Timber.i(throwable, msg)
            LogSeverity.WARNING -> Timber.w(throwable, msg)
            LogSeverity.ERROR -> Timber.e(throwable, msg)
        }
    }
}