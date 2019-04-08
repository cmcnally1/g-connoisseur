package com.cmcnally.guinnessconnoisseur

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    //Declaration of Google Map variable
    private lateinit var map: GoogleMap

    //Google maps api key
    //private val apiKey = getString(R.string.google_maps_key)

    //Fused location client provider
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    //Function is called when the Google Map is ready and initialised
    override fun onMapReady(googleMap: GoogleMap) {

        //The map just created is set to the map variable for the class
        map = googleMap

        //Enabling zoom control buttons
        map.uiSettings.isZoomControlsEnabled = true

        //Check if the user has already allowed location; if not, ask for permission
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        //Create the fused location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Try to get the user's last known location
        fusedLocationClient.lastLocation.addOnSuccessListener(this){ location ->

            if(location != null){
                //Create a latLng object for the current latitude and longitude
                val currentLatLng = LatLng(location.latitude, location.longitude)
                //Clear the map of all markers before getting the current location
                map.clear()
                //Move camera to show the current user position on the map
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))

            }
        }

        //Enable the location button
        map.isMyLocationEnabled = true

        //Set map type to be normal
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

    }

    companion object {
        //Location permission code constant
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
