package com.fractalwrench.britishsummer

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import okhttp3.ResponseBody

class CurrentWeatherRepository(private val weatherApi: WeatherApi) {

    fun getCurrentWeather(cityName: String): Flowable<CurrentWeather> {
        return weatherApi.getCurrentWeather(cityName).toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getWeatherForecast(cityName: String): Flowable<Forecast> {
        return weatherApi.getWeatherForecast(cityName).toFlowable(BackpressureStrategy.BUFFER)
    }

}
