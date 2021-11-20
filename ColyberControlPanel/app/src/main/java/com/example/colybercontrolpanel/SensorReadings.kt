package com.example.colybercontrolpanel

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class SensorReadings : AppCompatActivity() {

    private lateinit var chart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_readings)

        chart = findViewById<LineChart>(R.id.chart)


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
}