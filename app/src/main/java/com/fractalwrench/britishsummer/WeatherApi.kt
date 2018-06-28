package com.fractalwrench.britishsummer

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName: String): Observable<CurrentWeather>

    @GET("forecast")
    fun getWeatherForecast(@Query("q") cityName: String): Observable<Forecast>

}