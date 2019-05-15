package com.cmcnally.guinnessconnoisseur

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.cmcnally.guinnessconnoisseur.MainActivity.Companion.PUB_NAME_KEY
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Display the detail activity layout
        setContentView(R.layout.detail_activity)

        //Name of Pub selected
        val pubName = intent.getStringExtra(PUB_NAME_KEY)

        //Pub name TextView
        val pubNameTextView = findViewById<TextView>(R.id.nameText)

        //Set the text of the textviews to be the corresponding data
        pubNameTextView.text = pubName

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        
    }

    override fun onMapReady(p0: GoogleMap?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}