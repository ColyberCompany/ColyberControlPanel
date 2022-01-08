    package com.example.colybercontrolpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView

class PIDTuningActivity : AppCompatActivity() {
    private val pidControllersList = arrayOf("Leveling", "Heading", "AltHold")
    private val pidControllersComponents = arrayOf("P", "I", "D")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pidtuning)

        // Setup the list view
        val listView = findViewById<ListView>(R.id.pidComponentsListView)
        Log.e("asdf", "start")
        val pidComponentsAdapter = PIDTuningListAdapter(this, R.layout.pid_tuning_list_item, R.id.pidPartName, pidControllersComponents)
        listView.adapter = pidComponentsAdapter
        Log.e("asdf", "end")
    }
}