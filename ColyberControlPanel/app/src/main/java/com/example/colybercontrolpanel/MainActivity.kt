package com.example.colybercontrolpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

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

        val intent = Intent(this, SensorReadings::class.java).apply {
            putExtra(EXTRA_IPAddress, ipAddress)
            putExtra(EXTRA_IPPort, port)
        }

        startActivity(intent)
    }
}