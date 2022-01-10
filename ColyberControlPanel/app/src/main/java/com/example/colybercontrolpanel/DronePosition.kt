package com.example.colybercontrolpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.preference.PreferenceManager
import androidx.core.app.ActivityCompat
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.config.Configuration.*
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.Marker

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

        setUpMap()
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
            R.id.menuMap -> null
            R.id.menuSteering -> Intent(this, SteeringActivity::class.java)
            else -> null
        }

        if (intent != null){
            finish()
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
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

    private fun setUpMap() {
        // Pin to some initial location
        val mapController = map.controller
        mapController.setZoom(9.5)
        val startPoint = GeoPoint(48.8583, 2.2944)
        mapController.setCenter(startPoint)

        val startMarker = Marker(map)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(startMarker)

        //map.setTileSource(OnlineTileSourceBase("USGS Topo", 0, 18, 256, "", arrayOf("http://basemap.nationalmap.gov/ArcGIS/rest/services/USGSTopo/MapServer/tile/")), )
//        map.setTileSource(object : OnlineTileSourceBase(
//            "USGS Topo",
//            0,
//            18,
//            256,
//            "",
//            arrayOf("http://basemap.nationalmap.gov/ArcGIS/rest/services/USGSTopo/MapServer/tile/")
//        ) {
//            override fun getTileURLString(pMapTileIndex: Long): String {
//                return (baseUrl
//                        + MapTileIndex.getZoom(pMapTileIndex)
//                        + "/" + MapTileIndex.getY(pMapTileIndex)
//                        + "/" + MapTileIndex.getX(pMapTileIndex)
//                        + mImageFilenameEnding)
//            }
//        })

//        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(applicationContext), map)
//        locationOverlay.enableMyLocation()
//        map.overlays.add(locationOverlay)

        // Set up compass
        val compassOverlay = CompassOverlay(applicationContext, InternalCompassOrientationProvider(applicationContext), map)
        compassOverlay.enableCompass()
        map.overlays.add(compassOverlay)

        // Enable rotation gestures
        val rotationGestureOverlay = RotationGestureOverlay(map)
        rotationGestureOverlay.isEnabled
        map.setMultiTouchControls(true)
        map.overlays.add(rotationGestureOverlay)

        // Map Scale bar overlay
        // Note, "context" refers to your activity/application context.
        // You can simply do resources.displayMetrics when inside an activity.
        // When you aren't in an activity class, you will need to have passed the context
        // to the non-activity class.
        val dm = applicationContext.resources.displayMetrics
        val scaleBarOverlay = ScaleBarOverlay(map)
        scaleBarOverlay.setCentred(true)
        //play around with these values to get the location on screen in the right place for your application
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10)
        map.overlays.add(scaleBarOverlay)
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