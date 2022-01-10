package com.example.colybercontrolpanel

object Globals {
    var DroneIPAddress = ""
    var DronePort: Int = 0

    var fullscreenChartFlag = false

    object DroneData {
        // sensors
        var accX = 0.0f
        var accY = 0.0f
        var accZ = 0.0f
        var gyroX = 0.0f
        var gyroY = 0.0f
        var gyroZ = 0.0f
        var magnX = 0.0f
        var magnY = 0.0f
        var magnZ = 0.0f
        var pressure = 0.0f
        var btmRangefinder = 0.0f

        // calculated
        var angleX = 0.0f
        var angleY = 0.0f
        var angleZ = 0.0f
        var latitude = 0.0f
        var longitude = 0.0f
        var altitude = 0.0f
    }


    fun validateIPAddress(ipAddress: String): Boolean {
        for (byte: Char in ipAddress)
            if (!byte.isDigit() && byte != '.')
                return false
        return true
    }
}