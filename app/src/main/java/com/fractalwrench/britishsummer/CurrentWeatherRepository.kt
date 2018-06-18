package com.fractalwrench.britishsummer

import io.reactivex.Observable

class CurrentWeatherRepository(private val weatherApi: WeatherApi) {

    fun getCurrentWeather(cityName: String): Observable<CurrentWeather> {
        return weatherApi.getCurrentWeather(cityName)
    }

}
