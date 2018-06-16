package com.fractalwrench.britishsummer

import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AndroidModule::class)])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
}
