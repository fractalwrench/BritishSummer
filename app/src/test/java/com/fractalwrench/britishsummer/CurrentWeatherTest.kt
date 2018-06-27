package com.fractalwrench.britishsummer

import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Test

class CurrentWeatherTest {

    private val moshi = moshi()
    private val adapter = moshi.adapter<CurrentWeather>(CurrentWeather::class.java)

    @Test
    fun testSerialisation() {
        val json = javaClass.getResource("/current_weather.json").readText()
        assertNotNull(json)
        val response = adapter.fromJson(json)
        assertNotNull(response)
        fail()
    }
}