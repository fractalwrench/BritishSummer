package com.fractalwrench.britishsummer.weather

import com.fractalwrench.britishsummer.weather.current.CurrentWeatherViewModel
import com.fractalwrench.britishsummer.weather.forecast.ForecastViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

internal val weatherModule = module {
    single { WeatherRepository(get()) }
    viewModel { CurrentWeatherViewModel(get(), get("ui"), get()) }
    viewModel { ForecastViewModel(get(), get("ui"), get()) }
}