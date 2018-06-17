package com.fractalwrench.britishsummer

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CurrentWeatherTest {

    private lateinit var moshi: Moshi
    private lateinit var adapter: JsonAdapter<CurrentWeather>

    @Before
    fun setUp() {
        moshi = NetworkModule("").moshi()
        adapter = moshi.adapter<CurrentWeather>(CurrentWeather::class.java)
    }

    @Test
    fun testSerialisation() {
        val json = javaClass.getResource("/current_weather.json").readText()
        assertNotNull(json)
        val response = adapter.fromJson(json)
        assertNotNull(response)
    }
}