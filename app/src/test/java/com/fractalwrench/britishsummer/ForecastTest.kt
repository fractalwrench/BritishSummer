package com.fractalwrench.britishsummer

import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ForecastTest {

    lateinit var data: Forecast

    @Before
    fun setUp() {
        data = JsonResourceReader().readJsonResource("/forecast.json", Forecast::class.java)
    }

    @Test
    fun testSerialisation() {
        assertNotNull(data)

        assertEquals(40, data.cnt)

        with(data.city) {
            assertEquals(6618983, id)
            assertEquals("Zagreb - Centar", name)
            assertEquals("HR", country)
        }
    }
}