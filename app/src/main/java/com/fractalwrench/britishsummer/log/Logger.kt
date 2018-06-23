package com.fractalwrench.britishsummer.log

enum class LogSeverity {
    VERBOSE, DEBUG, INFO, WARNING, ERROR,
}

interface Loggable {
    fun log(msg: String, throwable: Throwable? = null, logSeverity: LogSeverity = LogSeverity.DEBUG)
}

class Logger(private val loggables: Array<Loggable>) : Loggable {
    override fun log(msg: String, throwable: Throwable?, logSeverity: LogSeverity) {
        loggables.forEach { it.log(msg, throwable, logSeverity) }
    }
}
