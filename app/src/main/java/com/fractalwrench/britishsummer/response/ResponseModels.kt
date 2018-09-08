package com.fractalwrench.britishsummer.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Arrays

/**
 * Represents the current weather in a given location
 */
@JsonClass(generateAdapter = true)
data class CurrentWeather(

    /**
     * The clouds at the current location
     */
    val clouds: Clouds?,

    /**
     * The GPS coordinates of the current location
     */
    val coord: Coord?,

    /**
     * Time of data calculation, unix, UTC
     */
    val dt: Long?,

    /**
     * The settlement's ID
     */
    val id: Long?,

    /**
     * Represents the main weather information (temperature, pressure, etc)
     */
    val main: Main?,

    /**
     * The settlement's name
     */
    val name: String?,

    /**
     * Country code/sunrise information
     */
    val sys: Sys?,

    /**
     * The visibility in metres
     */
    val visibility: Int?,

    /**
     * The weather conditions
     */
    val weather: Array<Weather>?,

    /**
     * The wind at the current location
     */
    val wind: Wind?,

    /**
     * The volume of rain in the near-past
     */
    val rain: Precipitation?,

    /**
     * The volume of snow in the near-past
     */
    val snow: Precipitation?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrentWeather

        if (clouds != other.clouds) return false
        if (coord != other.coord) return false
        if (dt != other.dt) return false
        if (id != other.id) return false
        if (main != other.main) return false
        if (name != other.name) return false
        if (sys != other.sys) return false
        if (visibility != other.visibility) return false
        if (!Arrays.equals(weather, other.weather)) return false
        if (wind != other.wind) return false
        if (rain != other.rain) return false
        if (snow != other.snow) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clouds?.hashCode() ?: 0
        result = 31 * result + (coord?.hashCode() ?: 0)
        result = 31 * result + (dt?.hashCode() ?: 0)
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + (main?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (sys?.hashCode() ?: 0)
        result = 31 * result + (visibility ?: 0)
        result = 31 * result + (weather?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (wind?.hashCode() ?: 0)
        result = 31 * result + (rain?.hashCode() ?: 0)
        result = 31 * result + (snow?.hashCode() ?: 0)
        return result
    }
}

/**
 * Represents the degree of cloudiness
 */
@JsonClass(generateAdapter = true)
data class Clouds(

    /**
     * Cloudiness, as a percentage
     */
    val all: Double?
)

/**
 * Represents GPS coordinates
 */
@JsonClass(generateAdapter = true)
data class Coord(

    /**
     * City geo location, latitude
     */
    val lat: Double?,

    /**
     * City geo location, longitude
     */
    val lon: Double?
)

/**
 * Represents the main weather information (temperature, pressure, etc)
 */
@JsonClass(generateAdapter = true)
data class Main(

    /**
     * Atmospheric pressure on the ground level, hPa
     */
    val grnd_level: Double?,

    /**
     * Humidity, %
     */
    val humidity: Double?,

    /**
     * Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
     */
    val pressure: Double?,

    /**
     * Atmospheric pressure on the sea level, hPa
     */
    val sea_level: Double?,

    /**
     * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     */
    val temp: Double?,

    /**
     * Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     */
    val temp_max: Double?,

    /**
     * Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     */
    val temp_min: Double?
)

/**
 * Country code/sunrise information
 */
@JsonClass(generateAdapter = true)
data class Sys(

    /**
     * Country code (GB, JP etc.)
     */
    val country: String?,

    /**
     * Sunrise time, unix, UTC
     */
    val sunrise: Long?,

    /**
     * Sunset time, unix, UTC
     */
    val sunset: Long?
)

/**
 * Represents the wind
 */
@JsonClass(generateAdapter = true)
data class Wind(

    /**
     * Wind direction, degrees (meteorological)
     */
    val deg: Double?,

    /**
     * Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
     */
    val speed: Double?
)

/**
 * Represents a weather condition. See https://openweathermap.org/weather-conditions
 */
@JsonClass(generateAdapter = true)
data class Weather(

    /**
     * Weather condition within the group
     */
    val description: String?,

    /**
     * Weather icon id
     */
    val icon: String?,

    /**
     * Weather condition id
     */
    val id: Long?,

    /**
     * Group of weather parameters (Rain, Snow, Extreme etc.)
     */
    val main: String?
)

/**
 * Represents future weather forecasts for a given settlement
 */
@JsonClass(generateAdapter = true)
data class Forecast(

    /**
     * The settlement to which the weather forecast applies
     */
    val city: City?,

    /**
     * The number of lines returned by this API call
     */
    val cnt: Int?,

    /**
     * The list of forecasts
     */
    val list: Array<WeatherPrediction>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Forecast

        if (city != other.city) return false
        if (cnt != other.cnt) return false
        if (!Arrays.equals(list, other.list)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = city?.hashCode() ?: 0
        result = 31 * result + (cnt ?: 0)
        result = 31 * result + (list?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}

/**
 * Represents information on a given settlement
 */
@JsonClass(generateAdapter = true)
data class City(

    /**
     * The GPS coordinates for the given settlement
     */
    val coord: Coord?,

    /**
     * The country which contains the settlement
     */
    val country: String?,

    /**
     * The settlement's weather ID
     */
    val id: Long?,

    /**
     * The name of the settlement
     */
    val name: String?
)

/**
 * The list of weather forecasts
 */
@JsonClass(generateAdapter = true)
data class WeatherPrediction(

    /**
     * The clouds at the current location
     */
    val clouds: Clouds?,

    /**
     * Time of data calculation, unix, UTC
     */
    val dt: Long?,

    /**
     * Represents the main weather information (temperature, pressure, etc)
     */
    val main: Main?,

    /**
     * The weather conditions
     */
    val weather: Array<Weather>?,

    /**
     * Country code/sunrise information
     */
    val sys: Sys?,

    /**
     * The wind at the current location
     */
    val wind: Wind?,

    /**
     * The volume of rain in the near-past
     */
    val rain: Precipitation?,

    /**
     * The volume of snow in the near-past
     */
    val snow: Precipitation?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherPrediction

        if (clouds != other.clouds) return false
        if (dt != other.dt) return false
        if (main != other.main) return false
        if (!Arrays.equals(weather, other.weather)) return false
        if (sys != other.sys) return false
        if (wind != other.wind) return false
        if (rain != other.rain) return false
        if (snow != other.snow) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clouds?.hashCode() ?: 0
        result = 31 * result + (dt?.hashCode() ?: 0)
        result = 31 * result + (main?.hashCode() ?: 0)
        result = 31 * result + (weather?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (sys?.hashCode() ?: 0)
        result = 31 * result + (wind?.hashCode() ?: 0)
        result = 31 * result + (rain?.hashCode() ?: 0)
        result = 31 * result + (snow?.hashCode() ?: 0)
        return result
    }
}

/**
 * Represents near-past precipitation
 */
data class Precipitation(

    /**
     * The volume of precipitation over 3 hours
     */
    @field:Json(name = "3h")
    val _3h: Double?
)
