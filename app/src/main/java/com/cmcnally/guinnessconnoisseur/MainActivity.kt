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
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), OnMapReadyCallback, MainPresenter.View{

    //MainPresenter by injection
    private val presenter: MainPresenter by inject()

    //Declaration of Google Map variable
    private lateinit var map: GoogleMap

    //Variable to hold the current Pubs
    private var currentPubs: Pubs? = null

    //Google maps api key
    //private val apiKey = getString(R.string.google_maps_key)

    //Fused location client provider
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Attach the current view to the presenter
        presenter.attachView(this)

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

                //User's latitude
                val userLatitude = currentLatLng.latitude.toString()
                //User's longitude
                val userLongitude = currentLatLng.longitude.toString()

                //Move camera to show the current user position on the map
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))

                //Tell the presenter to get the pubs for the user's current location
                presenter.getPubs(userLatitude, userLongitude)

            }
        }

        //Enable the location button
        map.isMyLocationEnabled = true

        //Set map type to be normal
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

    }

    private fun displayPubsOnMap(pubs: Pubs){

        //Loop through the charge points
        for (i in 0 until pubs.results!!.size) {
            //Check that the charge point location is not null
            if (pubs.results[i].geometry?.location?.lat != null && pubs.results[i].geometry?.location?.lng != null) {
                //Create a LatLng object for the charge point
                val location = LatLng(
                    pubs.results[i].geometry?.location?.lat!!.toDouble(),
                    pubs.results[i].geometry?.location?.lng!!.toDouble()
                )
                //Create a marker options object that will hold maker preferences like colour and title
                val markerOptions = MarkerOptions().position(location)

                //Marker title and information snippet that displays on click of the marker
                val titleStr = pubs.results[i].name
//                val snippetStr = "tap for more info"

                //add marker title and snippet to show info about the charge point
                markerOptions.title(titleStr)
//                    .snippet(snippetStr)

                //Marker object that displays on the map
                val locationMarker = map.addMarker(markerOptions)

                //associate the charge point data with the marker
                locationMarker.tag = pubs.results[i]
            }
        }

    }

    //Overridden function from the MainPresenter to return the pubs found
    override fun returnPubs(pubs: Pubs) {
        //Set the current pubs to be the pubs returned from the API
        currentPubs = pubs

        runOnUiThread {
            //Clear any current pins from the map
            map.clear()
            //Display pubs on the map
            displayPubsOnMap(currentPubs!!)
        }
        
    }


    companion object {
        //Location permission code constant
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
