package com.fractalwrench.britishsummer.log

import com.bugsnag.android.Client
import com.bugsnag.android.Severity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BugsnagLoggerTest {

    @Mock
    lateinit var client: Client

    @Test
    fun mapBugsnagSeverity() {
        val logger = BugsnagLogger(client)

        assertEquals(Severity.WARNING, logger.mapBugsnagSeverity(LogSeverity.WARNING))
        assertEquals(Severity.ERROR, logger.mapBugsnagSeverity(LogSeverity.ERROR))
        assertEquals(Severity.INFO, logger.mapBugsnagSeverity(LogSeverity.INFO))
        assertEquals(Severity.INFO, logger.mapBugsnagSeverity(LogSeverity.DEBUG))
    }
}