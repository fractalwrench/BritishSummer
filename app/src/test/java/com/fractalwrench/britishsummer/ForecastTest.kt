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

        with(data.city!!) {
            assertEquals(6618983, id)
            assertEquals("Zagreb - Centar", name)
            assertEquals("HR", country)
        }

        assertEquals(40, data.list!!.size)

        val list = data.list!![0]
        assertEquals(0.4475, list.rain?._3h)
        assertEquals(3.0, list.snow?._3h)
    }
}