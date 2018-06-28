package com.fractalwrench.britishsummer

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeather(
        val base: String,
        val clouds: Clouds,
        val cod: Int,
        val coord: Coord,
        val dt: Long,
        val id: Long,
        val main: Main,
        val name: String,
        val sys: Sys,
        val visibility: Int,
        val weather: Array<Weather>,
        val wind: Wind
)

@JsonClass(generateAdapter = true)
data class Clouds(val all: Int)

@JsonClass(generateAdapter = true)
data class Coord(val lat: Double, val lon: Double)

@JsonClass(generateAdapter = true)
data class Main(
        val grnd_level: Double?,
        val humidity: Double,
        val pressure: Double,
        val sea_level: Double?,
        val temp: Double,
        val temp_max: Double,
        val temp_min: Double
)

@JsonClass(generateAdapter = true)
data class Sys(
        val country: String,
        val id: Long,
        val message: Double,
        val sunrise: Long,
        val sunset: Long,
        val type: Int
)

@JsonClass(generateAdapter = true)
data class Wind(val deg: Double?, val speed: Double)

@JsonClass(generateAdapter = true)
data class Weather(
        val description: String,
        val icon: String,
        val id: Long,
        val main: String
)

@JsonClass(generateAdapter = true)
data class Forecast(
        val city: City,
        val cnt: Int,
        val cod: String,
        val list: Array<List>,
        val message: Double
)

@JsonClass(generateAdapter = true)
data class City(
        val coord: Coord,
        val country: String,
        val id: Long,
        val name: String
)

@JsonClass(generateAdapter = true)
data class List(
        val clouds: Clouds,
        val dt: Long,
        val dt_txt: String,
        val main: Main,
//        val rain: Clouds,
//        val sys: Clouds,
        val weather: Array<Weather>,
        val wind: Wind
)

// TODO rain + snow, check schema vs docs