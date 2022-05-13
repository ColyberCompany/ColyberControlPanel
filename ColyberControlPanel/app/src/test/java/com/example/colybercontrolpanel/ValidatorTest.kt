package com.example.colybercontrolpanel

import org.junit.Assert.*
import org.junit.Test

class ValidatorTest {
    @Test
    fun whenIPAddressIsValid() {
        var result = Validator.validateIPAddress("192.168.0.1")
        assertTrue(result)

        result = Validator.validateIPAddress("1.1.1.1")
        assertTrue(result)

        result = Validator.validateIPAddress("255.255.255.255")
        assertTrue(result)

        result = Validator.validateIPAddress("255.1.1.1")
        assertTrue(result)
    }

    @Test
    fun invalidIPAddressTest1() {
        var result = Validator.validateIPAddress("192")
        assertFalse(result)

        result = Validator.validateIPAddress("1233145235")
        assertFalse(result)

        result = Validator.validateIPAddress("")
        assertFalse(result)

        result = Validator.validateIPAddress("192.168.0.256")
        assertFalse(result)

        result = Validator.validateIPAddress("192.168.0.255.1")
        assertFalse(result)

        result = Validator.validateIPAddress("192.168.-1.1")
        assertFalse(result)

        result = Validator.validateIPAddress("...")
        assertFalse(result)

        result = Validator.validateIPAddress("192...")
        assertFalse(result)
    }

    @Test
    fun invalidIPAddressTest2() {
        var result = Validator.validateIPAddress("192.168.0.1 ")
        assertFalse(result)

        result = Validator.validateIPAddress("1.1. 1.1")
        assertFalse(result)

        result = Validator.validateIPAddress("1.1 .1.1")
        assertFalse(result)

        result = Validator.validateIPAddress("2 55.255.255.255")
        assertFalse(result)

        result = Validator.validateIPAddress("255 . 1 . 1 . 1 ")
        assertFalse(result)
    }

    @Test
    fun validIPPortTest1() {
        var result = Validator.validateIPPort("25565")
        assertTrue(result)

        result = Validator.validateIPPort("80")
        assertTrue(result)

        result = Validator.validateIPPort("404")
        assertTrue(result)
    }

    @Test
    fun invalidIPPortTest1() {
        var result = Validator.validateIPPort("")
        assertFalse(result)

        result = Validator.validateIPPort("asdf")
        assertFalse(result)

        result = Validator.validateIPPort(".")
        assertFalse(result)

        result = Validator.validateIPPort("-1234")
        assertFalse(result)

        result = Validator.validateIPPort("0")
        assertFalse(result)
    }
}



