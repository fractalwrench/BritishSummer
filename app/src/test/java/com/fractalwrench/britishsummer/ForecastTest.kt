package com.fractalwrench.britishsummer

import org.junit.Assert.assertNotNull
import org.junit.Test

class ForecastTest {

    @Test
    fun testSerialisation() {
        val response: Forecast = JsonResourceReader().readJsonResource("/forecast.json", Forecast::class.java)
        assertNotNull(response)
    }
}