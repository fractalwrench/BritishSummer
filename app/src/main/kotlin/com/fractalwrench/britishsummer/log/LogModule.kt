package com.fractalwrench.britishsummer.log

import com.bugsnag.android.Client
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

fun generateLogModule(client: Client): Module {
    return module {
        single { Logger(arrayOf(TimberLogger(), BugsnagLogger(get()))) }
        single { client }
    }
}
