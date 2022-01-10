package com.example.colybercontrolpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly

const val EXTRA_IPAddress = "com.example.colybercontrolpanel.IPAddress"
const val EXTRA_IPPort = "com.example.colybercontrolpanel.IPPort"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun wifiConnectOnClick(view: View)
    {
        val ipAddress = findViewById<EditText>(R.id.ipAddressTextView).text.toString()
        // TODO: check if ip address is valid
        val port = findViewById<EditText>(R.id.ipPortEditText).text.toString()

        if (Globals.validateIPAddress(ipAddress))
            Globals.DroneIPAddress = ipAddress
        else {
            Toast.makeText(this, "Invalid IP address", Toast.LENGTH_LONG).show()
            return
        }

        if (port.isDigitsOnly())
            Globals.DronePort = port.toInt()
        else {
            Toast.makeText(this, "Invalid port", Toast.LENGTH_LONG).show()
            return
        }

        UDPConn.connectAsync(this::connectingResultCallback)
    }

    private fun connectingResultCallback(connectingResult: Boolean) {
        if (connectingResult) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SensorReadings::class.java)
            startActivity(intent)
        }
        else
            Toast.makeText(this, "Connecting failed", Toast.LENGTH_LONG).show()
    }
}
