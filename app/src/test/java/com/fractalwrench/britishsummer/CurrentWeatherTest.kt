package com.fractalwrench.britishsummer

import org.junit.Assert.assertNotNull
import org.junit.Test

class CurrentWeatherTest {

    @Test
    fun testSerialisation() {
        val response: CurrentWeather = JsonResourceReader().readJsonResource("/current_weather.json", CurrentWeather::class.java)
        assertNotNull(response)
    }

}