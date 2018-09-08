package com.fractalwrench.britishsummer

import com.fractalwrench.britishsummer.weather.ViewPosition
import org.junit.Test

import org.junit.Assert.assertEquals

class UIStateKtTest {

    @Test
    fun toViewPosition() {
        assertEquals(ViewPosition.ERROR, UIState.Error<String>().toViewPosition())
        assertEquals(ViewPosition.PROGRESS, UIState.Progress<String>().toViewPosition())
        assertEquals(ViewPosition.PLACEHOLDER, UIState.Placeholder<String>().toViewPosition())
        assertEquals(ViewPosition.CONTENT, UIState.Content("").toViewPosition())
    }
}