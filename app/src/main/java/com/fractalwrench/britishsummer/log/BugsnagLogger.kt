package com.fractalwrench.britishsummer.log

import com.bugsnag.android.Client
import com.bugsnag.android.Severity

class BugsnagLogger(private val client: Client) : Loggable {

    override fun log(msg: String, throwable: Throwable?, logSeverity: LogSeverity) {

        // either log the message as the breadcrumb to help debugging crashes in the future,
        // or notify bugsnag of the non-fatal throwable

        if (throwable == null) {
            client.leaveBreadcrumb(msg)
        } else {
            val bugsnagSeverity = mapBugsnagSeverity(logSeverity)
            client.notify(throwable, bugsnagSeverity)
        }
    }

    private fun mapBugsnagSeverity(logSeverity: LogSeverity): Severity {
        return when (logSeverity) {
            LogSeverity.WARNING -> Severity.WARNING
            LogSeverity.ERROR -> Severity.ERROR
            else -> Severity.INFO
        }
    }
}