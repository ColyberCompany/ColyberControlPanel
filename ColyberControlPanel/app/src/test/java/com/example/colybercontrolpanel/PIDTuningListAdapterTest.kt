package com.example.colybercontrolpanel

import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class PIDTuningListAdapterTest {
    @Test
    fun valueToPercentTest1() {
        var result = PIDTuningListAdapter.valueToPercent(0.5f, 0f, 1f)
        assertEquals(0.5f, result)

        result = PIDTuningListAdapter.valueToPercent(0f, 0f, 1f)
        assertEquals(0f, result)
    }

    @Test
    fun valueToPercentExceptionTest() {
        assertThrows(Exception::class.java) {
            PIDTuningListAdapter.valueToPercent(123f, 5f, 4f)
        }

        assertThrows(Exception::class.java) {
            PIDTuningListAdapter.valueToPercent(123f, 1000f, 999.9f)
        }
    }
}