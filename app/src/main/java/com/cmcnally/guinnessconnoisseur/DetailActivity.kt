package com.cmcnally.guinnessconnoisseur

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.cmcnally.guinnessconnoisseur.MainActivity.Companion.PUB_LATITUDE_KEY
import com.cmcnally.guinnessconnoisseur.MainActivity.Companion.PUB_LONGITUDE_KEY
import com.cmcnally.guinnessconnoisseur.MainActivity.Companion.PUB_NAME_KEY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.detail_activity.*
import android.graphics.Color.parseColor
import android.graphics.Color.colorToHSV
import com.cmcnally.guinnessconnoisseur.R.color.colorAccent
import com.cmcnally.guinnessconnoisseur.R.color.colorPrimary


class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    //Google Map object
    private lateinit var map: GoogleMap

    //Object that holds a latitude and longitude
    private lateinit var latLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Display the detail activity layout
        setContentView(R.layout.detail_activity)

        //Name of Pub selected
        val pubName = intent.getStringExtra(PUB_NAME_KEY)
        val latitude = intent.getStringExtra(PUB_LATITUDE_KEY)
        val longitude = intent.getStringExtra(PUB_LONGITUDE_KEY)

        //Create a LatLng object from the received latitude and longitude
        latLng = LatLng(latitude.toDouble(), longitude.toDouble())

        //Pub name TextView
        val pubNameTextView = findViewById<TextView>(R.id.nameText)

        //Set the text of the textviews to be the corresponding data
        pubNameTextView.text = pubName

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Creation of an Intent object that will pass the pubs latitude and longitude
        //out to the google maps application to allow turn-by-turn navigation to the pub
        var gmIntentUri: Uri = Uri.parse("google.navigation:q=$latitude,$longitude")
        var mapIntent = Intent(Intent.ACTION_VIEW, gmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        //Implementation of the button that will activate the turn-by-turn navigation through google maps
        goThereButton.setOnClickListener {
            startActivity(mapIntent)
        }
    }

    //onMapReady and setUpMap set the map up when the activity is started
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isMyLocationButtonEnabled = false
        map.uiSettings.isZoomControlsEnabled = false

        val markerOptions = MarkerOptions()
            .position(latLng)

        map.addMarker(markerOptions)

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
    }


}