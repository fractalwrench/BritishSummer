package com.fractalwrench.britishsummer

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName: String): Observable<CurrentWeather>
}
