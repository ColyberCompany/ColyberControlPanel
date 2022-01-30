package com.example.colybercontrolpanel

import androidx.core.text.isDigitsOnly

object Validator {

    fun validateIPAddress(ipAddress: String): Boolean {
        if (ipAddress.isEmpty())
            return false
        val ipAddressSplit = ipAddress.split('.')
        if (ipAddressSplit.size != 4)
            return false
        for (ipPart: String in ipAddressSplit) {
            val ipPartInt = ipPart.toIntOrNull() ?: return false
            if (ipPartInt < 0 || ipPartInt > 255)
                return false
        }

        return true // ultimately
    }

    fun validateIPPort(port: String): Boolean {
        if (port.isEmpty())
            return false
        val portInt = port.toIntOrNull() ?: return false
        if (portInt <= 0)
            return false

        return true
    }
}