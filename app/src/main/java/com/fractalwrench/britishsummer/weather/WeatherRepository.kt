package com.fractalwrench.britishsummer.weather

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class WeatherRepository(private val weatherApi: WeatherApi) {

    fun getCurrentWeather(cityName: String): Flowable<CurrentWeather> {
        return weatherApi.getCurrentWeather(cityName).toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getWeatherForecast(cityName: String): Flowable<Forecast> {
        return weatherApi.getWeatherForecast(cityName).toFlowable(BackpressureStrategy.BUFFER)
    }
}
