package com.example.colybercontrolpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class SensorReadings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_readings)
    }

    // TODO: remove this onClick
    fun testOnClick(view: View) {
        Log.e("asdf", "qwer")
        var edittext: EditText
        if (view is EditText)
            edittext = view
        else
            return

        Log.e("as", edittext.text.toString())

        val intent = Intent(this, DronePosition::class.java)
        startActivity(intent)
    }
}