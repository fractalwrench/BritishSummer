package com.fractalwrench.britishsummer

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import org.reactivestreams.Publisher

class CurrentWeatherRepository(private val weatherApi: WeatherApi) {

    fun getCurrentWeather(cityName: String): Observable<CurrentWeather> {
        return weatherApi.getCurrentWeather(cityName)
//                .toFlowable(BackpressureStrategy.BUFFER)
    }

}
