package com.fractalwrench.britishsummer

import com.fractalwrench.britishsummer.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AndroidModule::class), (NetworkModule::class), (DataModule::class)])
@Singleton
interface AppComponent {
    fun inject(fragment: MainFragment)
}
