package com.fractalwrench.britishsummer

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
            // TODO others
        }


        with(data.coord) {
            assertEquals(51.51, lat)
            assertEquals(-0.13, lon)
        }
        with(data.wind) {
            assertEquals(4.1, speed)
            assertEquals(80.0, deg)
        }

        assertEquals(90.0, data.clouds.all)

    }

}