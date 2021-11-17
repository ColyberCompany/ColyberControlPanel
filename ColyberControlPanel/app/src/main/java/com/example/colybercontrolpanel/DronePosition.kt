package com.example.colybercontrolpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.View
import androidx.core.app.ActivityCompat
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.config.Configuration.*

// osmdroid github: https://github.com/osmdroid/osmdroid
// Example: https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Kotlin)

class DronePosition : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private lateinit var map : MapView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        setContentView(R.layout.activity_drone_position)

        map = findViewById<MapView>(R.id.testMapView)
        map.setTileSource(TileSourceFactory.MAPNIK)

        val mapController = map.controller
        mapController.setZoom(9.5)
        val startPoint = GeoPoint(48.8583, 2.2944);
        mapController.setCenter(startPoint);
    }

    override fun onPause() {
        super.onPause()

        map.onPause()
    }

    override fun onResume() {
        super.onResume()

        map.onResume()
    }

    // Don't know why need this
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>();
        var i = 0;
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i]);
            i++;
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    fun pinToDroneOnClick(view: View)
    {
        // TODO: implement pin to drone onClick
        val mapController = map.controller
        mapController.setZoom(13.0)
        val startPoint = GeoPoint(48.8583, 2.2944);
        mapController.setCenter(startPoint);
    }
}