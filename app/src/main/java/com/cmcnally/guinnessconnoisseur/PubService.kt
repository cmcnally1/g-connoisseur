package com.cmcnally.guinnessconnoisseur

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class PubService {

    var listener: DataListener? = null

    private val apiKey = "AIzaSyA2W69fcSdTDZEptQSLI5Q7iKn3E7rljvY"

    fun fetchData(latitude: String, longitude: String){

        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$latitude,$longitude&type=bar&rankby=distance&key=$apiKey"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call, response: Response) {

                val body = response.body()?.string()

                val gson = GsonBuilder().create()

                val result = gson.fromJson(body, Pubs::class.java)

                listener?.onResponseReceived(result)
            }

            override fun onFailure(call: Call, e: IOException) {
                //Print message if an error occurs while fetching information
                println("Failed to execute request")
            }
        })
    }

    interface DataListener {
        fun onResponseReceived(response: Pubs)
    }
}