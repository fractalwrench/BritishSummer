package com.fractalwrench.britishsummer

import com.fractalwrench.britishsummer.response.CurrentWeather
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CurrentWeatherTest {

    lateinit var data: CurrentWeather

    @Before
    fun setUp() {
        data = JsonResourceReader().readJsonResource("/current_weather.json", CurrentWeather::class.java)
    }

    @Test
    fun testSerialisation() {
        with(data) {
            assertEquals(1485789600, dt)
            assertEquals(2643743, id)
            assertEquals("London", name)
            assertEquals(10000, visibility)
        }

        with(data.coord!!) {
            assertEquals(51.51, lat)
            assertEquals(-0.13, lon)
        }
        with(data.wind!!) {
            assertEquals(4.1, speed)
            assertEquals(80.0, deg)
        }

        assertEquals(90.0, data.clouds!!.all)

        with(data.main!!) {
            assertEquals(280.32, temp)
            assertEquals(1012.0, pressure)
            assertEquals(81.0, humidity)
            assertEquals(279.15, temp_min)
            assertEquals(281.15, temp_max)
        }

        with(data.sys!!) {
            assertEquals("GB", country)
            assertEquals(1485762037, sunrise)
            assertEquals(1485794875, sunset)
        }

        assertEquals(1, data.weather!!.size)
        val weather = data.weather!![0]

        assertEquals(300, weather.id)
        assertEquals("Drizzle", weather.main)
        assertEquals("light intensity drizzle", weather.description)
        assertEquals("09d", weather.icon)

        assertEquals(3.0, data.rain?._3h)
        assertEquals(3.0, data.snow?._3h)
    }
}