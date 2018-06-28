package com.fractalwrench.britishsummer

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class CurrentWeatherRepository(private val weatherApi: WeatherApi) {

    fun getCurrentWeather(cityName: String): Flowable<CurrentWeather> {
        return weatherApi.getCurrentWeather(cityName).toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getWeatherForecast(cityName: String): Flowable<Forecast> {
        return weatherApi.getWeatherForecast(cityName).toFlowable(BackpressureStrategy.BUFFER)
    }
}
