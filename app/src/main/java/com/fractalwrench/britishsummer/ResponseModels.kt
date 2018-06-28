package com.fractalwrench.britishsummer

import com.squareup.moshi.JsonClass

// TODO how to handle nullable values? The API does not guarantee data.

@JsonClass(generateAdapter = true)
data class CurrentWeather(

    /**
     * The clouds at the current location
     */
    val clouds: Clouds,

    /**
     * The GPS coordinates of the current location
     */
    val coord: Coord,

    /**
     * Time of data calculation, unix, UTC
     */
    val dt: Long,

    /**
     * The settlement's ID
     */
    val id: Long,

    // TODO
    val main: Main,

    /**
     * The settlement's name
     */
    val name: String,

    // TODO
    val sys: Sys,

    /**
     * The visibility in metres
     */
    val visibility: Int?,

    // TODO
    val weather: Array<Weather>,

    /**
     * The wind at the current location
     */
    val wind: Wind
)

/**
 * Represents the degree of cloudiness
 */
@JsonClass(generateAdapter = true)
data class Clouds(

    /**
     * Cloudiness, as a percentage
     */
    val all: Double
)

/**
 * Represents GPS coordinates
 */
@JsonClass(generateAdapter = true)
data class Coord(

    /**
     * City geo location, latitude
     */
    val lat: Double,

    /**
     * City geo location, longitude
     */
    val lon: Double
)

// TODO
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

// TODO
@JsonClass(generateAdapter = true)
data class Sys(
    val country: String,
    val id: Long,
    val message: Double,
    val sunrise: Long,
    val sunset: Long,
    val type: Int
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
    val speed: Double
)

// TODO
@JsonClass(generateAdapter = true)
data class Weather(
    val description: String,
    val icon: String,
    val id: Long,
    val main: String
)

// TODO deal with remaining

//weather (more info Weather condition codes)
//weather.id Weather condition id
//weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
//weather.description Weather condition within the group
//weather.icon Weather icon id
//main
//main.temp Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
//main.pressure Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
//main.humidity Humidity, %
//main.temp_min Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
//main.temp_max Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
//main.sea_level Atmospheric pressure on the sea level, hPa
//main.grnd_level Atmospheric pressure on the ground level, hPa
//rain
//rain.3h Rain volume for the last 3 hours
//snow
//snow.3h Snow volume for the last 3 hours
//sys
//sys.country Country code (GB, JP etc.)
//sys.sunrise Sunrise time, unix, UTC
//sys.sunset Sunset time, unix, UTC

@JsonClass(generateAdapter = true)
data class Forecast(

    /**
     * The settlement to which the weather forecast applies
     */
    val city: City,

    /**
     * The number of lines returned by this API call
     */
    val cnt: Int,

    /**
     * The list of forecasts
     */
    val list: Array<List>
)

@JsonClass(generateAdapter = true)
data class City(

    /**
     * The GPS coordinates for the given settlement
     */
    val coord: Coord,

    /**
     * The country which contains the settlement
     */
    val country: String,

    /**
     * The settlement's weather ID
     */
    val id: Long,

    /**
     * The name of the settlement
     */
    val name: String
)

// TODO
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


