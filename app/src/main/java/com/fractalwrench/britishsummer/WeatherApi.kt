package com.fractalwrench.britishsummer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // todo add an interceptor for the appid

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") appId: String
    ): Call<CurrentWeather>
}
