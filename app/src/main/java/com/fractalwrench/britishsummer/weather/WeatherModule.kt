package com.fractalwrench.britishsummer.weather

import com.fractalwrench.britishsummer.weather.current.CurrentWeatherViewModel
import org.koin.android.architecture.ext.koin.viewModel
import org.koin.dsl.module.module

internal val weatherModule = module {
    bean { WeatherRepository(get()) }
    viewModel { CurrentWeatherViewModel(get(), get("ui")) }
}