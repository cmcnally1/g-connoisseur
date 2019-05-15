package com.cmcnally.guinnessconnoisseur

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cmcnally.guinnessconnoisseur.MainActivity.Companion.PUB_NAME_KEY

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Display the detail activity layout
        setContentView(R.layout.detail_activity)

        val pubName = intent.getStringExtra(PUB_NAME_KEY)
    }

}