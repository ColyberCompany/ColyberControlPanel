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
import androidx.constraintlayout.widget.ConstraintLayout
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

    private var fullscreenChartFlag = false

    private var plotType: PlotType = PlotType.AngleXY



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

        series1.color = Color.RED
        series2.color = Color.GREEN
        series3.color = Color.BLUE

        graph.setBackgroundColor(Color.WHITE)
    }

    override fun onResume() {
        super.onResume()
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

    fun chartOnClick(view: View) {
        val params = graph.layoutParams as ConstraintLayout.LayoutParams

        if (fullscreenChartFlag) {
            params.topToTop = ConstraintLayout.LayoutParams.UNSET
            params.topToBottom = angleYEditText.id

            //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        else {
            //params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            //params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            //params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID

            //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        fullscreenChartFlag = !fullscreenChartFlag
        graph.requestLayout()
    }

    fun selectChartDataOnClick(view: View) {
        plotType = when (view.id) {
            R.id.editTextAngleX, R.id.editTextAngleY -> PlotType.AngleXY
            R.id.editTextAngleZ -> PlotType.AngleZ
            R.id.editTextPosLong, R.id.editTextPosLat -> PlotType.PositionXY
            R.id.editTextPosAlt -> PlotType.PositionZ
            R.id.editTextAccX, R.id.editTextAccY, R.id.editTextAccZ -> PlotType.AccXYZ
            R.id.editTextGyroX, R.id.editTextGyroY, R.id.editTextGyroZ -> PlotType.GyroXYZ
            R.id.editTextMagnX, R.id.editTextMagnY, R.id.editTextMagnZ -> PlotType.MagnXYZ
            R.id.editTextPressure -> PlotType.Pressure
            R.id.editTextBtmRangefinder -> PlotType.BtmRangefinder
            else -> PlotType.AngleXY
        }
    }

    private val updateReadings = object : Runnable {
        override fun run() {
            updateRealTimeValues()
            updateDynamicChart()

            handler.postDelayed(this, UpdateValuesTimerInterval)
        }
    }

    private fun updateRealTimeValues() {
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
    }

    private fun updateDynamicChart() {
        var ser1Data = Float.NaN
        var ser2Data = Float.NaN
        var ser3Data = Float.NaN

        // set values of proper data to plot
        when (plotType) {
            PlotType.AngleXY -> {
                ser1Data = Globals.DroneData.angleX
                ser2Data = Globals.DroneData.angleY
            }
            PlotType.AngleZ ->
                ser1Data = Globals.DroneData.angleZ
            PlotType.PositionXY -> {
                ser1Data = Globals.DroneData.latitude
                ser2Data = Globals.DroneData.longitude
            }
            PlotType.PositionZ ->
                ser1Data = Globals.DroneData.altitude
            PlotType.AccXYZ -> {
                ser1Data = Globals.DroneData.accX
                ser2Data = Globals.DroneData.accY
                ser3Data = Globals.DroneData.accZ
            }
            PlotType.GyroXYZ -> {
                ser1Data = Globals.DroneData.gyroX
                ser2Data = Globals.DroneData.gyroY
                ser3Data = Globals.DroneData.gyroZ
            }
            PlotType.MagnXYZ -> {
                ser1Data = Globals.DroneData.magnX
                ser2Data = Globals.DroneData.magnY
                ser3Data = Globals.DroneData.magnZ
            }
            PlotType.Pressure ->
                ser1Data = Globals.DroneData.pressure
            PlotType.BtmRangefinder ->
                ser1Data = Globals.DroneData.btmRangefinder
        }

        // add data to data series (plot data)
        if (!ser1Data.isNaN())
            series1.appendData(DataPoint(lastXValue, ser1Data.toDouble()), true, MaxDataPoints)
        else
            series1.resetData(emptyArray())
        if (!ser2Data.isNaN())
            series2.appendData(DataPoint(lastXValue, ser2Data.toDouble()), true, MaxDataPoints)
        else
            series2.resetData(emptyArray())
        if (!ser3Data.isNaN())
            series3.appendData(DataPoint(lastXValue, ser3Data.toDouble()), true, MaxDataPoints)
        else
            series3.resetData(emptyArray())

        lastXValue += 1.0 // move chart to the next sample
        lastXValue += 1.0 // move chart to the next sample
    }

    enum class PlotType {
        AngleXY,
        AngleZ,
        PositionXY,
        PositionZ,
        AccXYZ,
        GyroXYZ,
        MagnXYZ,
        Pressure,
        BtmRangefinder
    }
}
