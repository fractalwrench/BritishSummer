package com.fractalwrench.britishsummer

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.module

internal val schedulerModule = module {
    single("ui") { AndroidSchedulers.mainThread() }
    single("io") { Schedulers.io() }
    single("compute") { Schedulers.computation() }
}
