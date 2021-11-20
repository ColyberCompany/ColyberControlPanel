package com.example.colybercontrolpanel

object Globals {
    var DroneIPAddress = ""
    var DronePort: Int = 0

    object DroneData {
        // sensors
        var accX = 0.0
        var accY = 0.0
        var accZ = 0.0
        var gyroX = 0.0
        var gyroY = 0.0
        var gyroZ = 0.0
        var magnX = 0.0
        var magnY = 0.0
        var magnZ = 0.0
        var pressure = 0.0
        var btmRangefinder = 0.0

        // calculated
        var angleX = 0.0
        var angleY = 0.0
        var angleZ = 0.0
        var latitude = 0.0
        var longitude = 0.0
        var altitude = 0.0
    }


    fun validateIPAddress(ipAddress: String): Boolean {
        for (byte: Char in ipAddress)
            if (!byte.isDigit() && byte != '.')
                return false
        return true
    }
}