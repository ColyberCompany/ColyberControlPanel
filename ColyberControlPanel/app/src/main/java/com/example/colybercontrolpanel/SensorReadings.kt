package com.example.colybercontrolpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

// Chart library: https://github.com/PhilJay/MPAndroidChart
// Simple LineChart getting started: https://weeklycoding.com/mpandroidchart-documentation/getting-started/

class SensorReadings : AppCompatActivity() {

    private lateinit var chart: LineChart

    private lateinit var accXEditText: EditText
    private lateinit var accYEditText: EditText
    private lateinit var accZEditText: EditText
    private lateinit var gyroXEditText: EditText
    private lateinit var gyroYEditText: EditText
    private lateinit var gyroZEditText: EditText
    private lateinit var magnXEditText: EditText
    private lateinit var magnYEditText: EditText
    private lateinit var magnZEditText: EditText
    private lateinit var pressureEditText: EditText
    private lateinit var bottomRangefinderEditText: EditText
    private lateinit var longitudeEditText: EditText
    private lateinit var latitudeEditText: EditText
    private lateinit var altitudeEditText: EditText
    private lateinit var angleXEditText: EditText
    private lateinit var angleYEditText: EditText
    private lateinit var angleZEditText: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_readings)

        chart = findViewById<LineChart>(R.id.chart)

        accXEditText = findViewById(R.id.editTextAccX)
        accYEditText = findViewById(R.id.editTextAccY)
        accZEditText = findViewById(R.id.editTextAccZ)
        gyroXEditText = findViewById(R.id.editTextGyroX)
        gyroYEditText = findViewById(R.id.editTextGyroY)
        gyroZEditText = findViewById(R.id.editTextGyroZ)
        magnXEditText = findViewById(R.id.editTextMagnX)
        magnYEditText = findViewById(R.id.editTextMagnY)
        magnZEditText = findViewById(R.id.editTextMagnZ)
        pressureEditText = findViewById(R.id.editTextPressure)
        bottomRangefinderEditText = findViewById(R.id.editTextBtmRangefinder)
        longitudeEditText = findViewById(R.id.editTextPosLong)
        latitudeEditText = findViewById(R.id.editTextPosLat)
        altitudeEditText = findViewById(R.id.editTextPosAlt)
        angleXEditText = findViewById(R.id.editTextAngleX)
        angleYEditText = findViewById(R.id.editTextAngleY)
        angleZEditText = findViewById(R.id.editTextAngleZ)

        Thread(updateReadings).start()


        // TODO: remove this test values of chart
        val entries = mutableListOf<Entry>()
        for (i in 1..10)
            entries.add(Entry(i.toFloat(), i.toFloat()))

        val dataSet = LineDataSet(entries, "Label")
        dataSet.color = 0xFF0000

        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate() // refresh
    }

    // TODO: remove this onClick
    fun testOnClick(view: View) {
        Log.e("asdf", "qwer")
        val edittext: EditText
        if (view is EditText)
            edittext = view
        else
            return

        Log.e("as", edittext.text.toString())

        val intent = Intent(this, DronePosition::class.java)
        startActivity(intent)
    }

    private val updateReadings = Runnable {
        while (true) {
            accXEditText.setText(Globals.DroneData.accX.toString())
            accYEditText.setText(Globals.DroneData.accY.toString())
            accZEditText.setText(Globals.DroneData.accZ.toString())
            gyroXEditText.setText(Globals.DroneData.gyroX.toString())
            gyroYEditText.setText(Globals.DroneData.gyroY.toString())
            gyroZEditText.setText(Globals.DroneData.gyroZ.toString())
            magnXEditText.setText(Globals.DroneData.magnX.toString())
            magnYEditText.setText(Globals.DroneData.magnY.toString())
            magnZEditText.setText(Globals.DroneData.magnZ.toString())

            pressureEditText.setText(Globals.DroneData.pressure.toString())
            bottomRangefinderEditText.setText(Globals.DroneData.btmRangefinder.toString())
            longitudeEditText.setText(Globals.DroneData.longitude.toString())
            latitudeEditText.setText(Globals.DroneData.latitude.toString())
            altitudeEditText.setText(Globals.DroneData.altitude.toString())

            angleXEditText.setText(Globals.DroneData.angleX.toString())
            angleYEditText.setText(Globals.DroneData.angleY.toString())
            angleZEditText.setText(Globals.DroneData.angleZ.toString())

            Thread.sleep(100)
        }
    }
}
