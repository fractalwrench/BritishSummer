package com.fractalwrench.britishsummer

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun currentWeatherRepository(weatherApi: WeatherApi) = CurrentWeatherRepository(weatherApi)
}