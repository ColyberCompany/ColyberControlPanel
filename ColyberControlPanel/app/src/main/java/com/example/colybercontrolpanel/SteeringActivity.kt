package com.example.colybercontrolpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.github.controlwear.virtual.joystick.android.JoystickView

class SteeringActivity : AppCompatActivity() {

    private lateinit var leftJoystick: JoystickView
    private lateinit var rightJoystick: JoystickView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steering)

        leftJoystick = findViewById(R.id.leftJoystick)
        rightJoystick = findViewById(R.id.rightJoystick)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent? = when (item.itemId) {
            R.id.menuSensorsReadings -> Intent(this, SensorReadings::class.java)
            R.id.menuPIDTuning -> Intent(this, PIDTuningActivity::class.java)
            R.id.menuMap -> Intent(this, DronePosition::class.java)
            R.id.menuSteering -> null
            else -> null
        }

        if (intent != null){
            finish()
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}