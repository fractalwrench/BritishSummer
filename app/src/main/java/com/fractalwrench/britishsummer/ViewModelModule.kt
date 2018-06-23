package com.fractalwrench.britishsummer

import com.fractalwrench.britishsummer.ui.main.CurrentWeatherViewModel
import org.koin.android.architecture.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { CurrentWeatherViewModel(get()) }
}