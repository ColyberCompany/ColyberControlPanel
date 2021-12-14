package com.example.colybercontrolpanel

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

// Chart library: https://github.com/PhilJay/MPAndroidChart
// Simple LineChart getting started: https://weeklycoding.com/mpandroidchart-documentation/getting-started/

class SensorReadings : AppCompatActivity() {

    private val handler: Handler = Handler(Looper.getMainLooper())

    //private lateinit var chart: LineChart
    private lateinit var graph: GraphView

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

        //chart = findViewById<LineChart>(R.id.chart)
        graph = findViewById(R.id.graph)

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

/*
        // TODO: remove this test values of chart
        val entries = mutableListOf<Entry>()
        for (i in 1..10)
            entries.add(Entry(i.toFloat(), i.toFloat()))

        val dataSet = LineDataSet(entries, "Label")
        dataSet.apply {
            //color = 0xFF0000
            lineWidth = 1.5f
            setDrawCircles(false)
            setDrawValues(false)
            setDrawCircleHole(false)

            color = ColorTemplate.getHoloBlue()
            valueTextColor = ColorTemplate.getHoloBlue()
            fillAlpha = 65
            fillColor = ColorTemplate.getHoloBlue()
            highLightColor = Color.rgb(244, 117, 117)
        }
        dataSet.color = 0xFF0000

        val lineData = LineData(dataSet)
        lineData.apply {
            setValueTextColor(Color.WHITE)
            setValueTextSize(9f)
        }
        chart.data = lineData
        chart.invalidate() // refresh*/


        val series = LineGraphSeries<DataPoint>()
        for (i in 1..10)
            series.appendData(DataPoint(i.toDouble(), i.toDouble()), false, 40)

        val series2 = LineGraphSeries<DataPoint>()
        for (i in 1..10)
            series2.appendData(DataPoint(i.toDouble(), (i + 10).toDouble()), false, 40)

        graph.addSeries(series)
        graph.addSeries(series2)
    }

    override fun onResume() {
        super.onResume();
        handler.postDelayed(updateReadings, 100)
    }

    override fun onPause() {
        handler.removeCallbacks(updateReadings)
        super.onPause()
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

    private val updateReadings = object : Runnable {
        override fun run() {
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

            handler.postDelayed(this, 100)
        }
    }
}
