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

        result = PIDTuningListAdapter.valueToPercent(1f, 0f, 1f)
        assertEquals(1f, result)

        result = PIDTuningListAdapter.valueToPercent(-2f, -2f, -1f)
        assertEquals(0f, result)
    }

    @Test
    fun valueToPercentTest2() {
        var result = PIDTuningListAdapter.valueToPercent(1f, 0f, 1000f)
        assertEquals(0.001f, result)

        result = PIDTuningListAdapter.valueToPercent(10f, 0f, 1000f)
        assertEquals(0.01f, result)

        result = PIDTuningListAdapter.valueToPercent(950f, 900f, 1000f)
        assertEquals(0.5f, result)

        result = PIDTuningListAdapter.valueToPercent(0f, -100f, 100f)
        assertEquals(0.5f, result)
    }

    @Test
    fun valueToPercentOverrangeTest1() {
        var result = PIDTuningListAdapter.valueToPercent(200f, 0f, 100f)
        assertEquals(2f, result)

        result = PIDTuningListAdapter.valueToPercent(300f, 0f, 100f)
        assertEquals(3f, result)

        result = PIDTuningListAdapter.valueToPercent(25f, 50f, 100f)
        assertEquals(-0.5f , result)

        result = PIDTuningListAdapter.valueToPercent(0f, 50f, 100f)
        assertEquals(-1f , result)

        result = PIDTuningListAdapter.valueToPercent(-100f, 50f, 100f)
        assertEquals(-3f , result)
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