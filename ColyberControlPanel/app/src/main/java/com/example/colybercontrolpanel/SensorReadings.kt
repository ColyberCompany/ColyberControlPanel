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
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

// Chart library: https://github.com/PhilJay/MPAndroidChart
// Simple LineChart getting started: https://weeklycoding.com/mpandroidchart-documentation/getting-started/

// Finally used graph library: https://github.com/jjoe64/GraphView

private const val UpdateValuesTimerInterval: Long = 50
private const val MaxDataPoints: Int = 50


class SensorReadings : AppCompatActivity() {
    private val handler: Handler = Handler(Looper.getMainLooper())

    private lateinit var graph: GraphView
    private val series1 = LineGraphSeries<DataPoint>()
    private val series2 = LineGraphSeries<DataPoint>()
    private val series3 = LineGraphSeries<DataPoint>()
    private var lastXValue = 0.0

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

        graph = findViewById(R.id.graph)

        graph.addSeries(series1)
        graph.addSeries(series2)
        graph.addSeries(series3)
        graph.viewport.apply {
            isXAxisBoundsManual = true
            setMinX(0.0)
            setMaxX(MaxDataPoints.toDouble())
        }

        series2.color = Color.GREEN
    }

    override fun onResume() {
        super.onResume();
        handler.postDelayed(updateReadings, UpdateValuesTimerInterval)
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


            series1.appendData(DataPoint(lastXValue, Globals.DroneData.angleX.toDouble()), true, MaxDataPoints)
            series2.appendData(DataPoint(lastXValue, Globals.DroneData.angleY.toDouble()), false, MaxDataPoints)
            lastXValue += 1.0

            handler.postDelayed(this, UpdateValuesTimerInterval)
        }
    }
}
