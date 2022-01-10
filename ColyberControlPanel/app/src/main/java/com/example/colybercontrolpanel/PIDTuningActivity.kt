package com.example.colybercontrolpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*

private const val AutoSendingInterval: Long = 500


class PIDTuningActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    private val pidControllersList = arrayOf("Leveling", "Heading", "AltHold")
    private val pidControllersComponents = arrayOf("P", "I", "D")

    private lateinit var sendButton: Button
    private lateinit var autoSendingCheckBox: CheckBox
    private lateinit var pidControllerSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pidtuning)

        sendButton = findViewById(R.id.sendButton)
        autoSendingCheckBox = findViewById(R.id.autoSendingCheckbox)
        pidControllerSpinner = findViewById(R.id.pidControllerSpinner)

        // Set up pid controllers Spinner
        val pidControllersSpinnerAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, pidControllersList)
        pidControllerSpinner.adapter = pidControllersSpinnerAdapter
        pidControllerSpinner.onItemSelectedListener = pidControllersSpinnerOnItemSelect
        autoSendingCheckBox.setOnCheckedChangeListener(onAutoSendingCheckChange)

        // Setup the ListView
        val listView = findViewById<ListView>(R.id.pidComponentsListView)
        val pidComponentsAdapter = PIDTuningListAdapter(this, R.layout.pid_tuning_list_item, R.id.pidPartName, pidControllersComponents)
        listView.adapter = pidComponentsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent? = when (item.itemId) {
            R.id.menuSensorsReadings -> Intent(this, SensorReadings::class.java)
            R.id.menuPIDTuning -> null
            R.id.menuMap -> Intent(this, DronePosition::class.java)
            R.id.menuSteering -> Intent(this, SteeringActivity::class.java)
            else -> null
        }

        if (intent != null){
            finish()
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    private val pidControllersSpinnerOnItemSelect = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // TODO: implement pid controllers spinner on item selected
        }
    }

    fun sendButtonOnClick(view: View) {
        UDPConn.sendPID()
    }

    private val onAutoSendingCheckChange =
        CompoundButton.OnCheckedChangeListener { p0, state ->
            sendButton.isEnabled = !state
            if (state) handler.postDelayed(autoSendingRunnable, AutoSendingInterval)
            else handler.removeCallbacks(autoSendingRunnable)
        }



    private val autoSendingRunnable = object : Runnable {
        override fun run() {
            UDPConn.sendPID()

            handler.postDelayed(this, AutoSendingInterval)
        }
    }
}