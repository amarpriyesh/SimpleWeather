package com.example.android.simpleweather

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.JsonObject

class ZipCodeReader(private val resources: Resources) {
    fun getLatLong(zipCode: String): LocationInfo? {
        val text = resources.openRawResource(R.raw.zipcodes)
            .bufferedReader().use { it.readText() }
        val zipCodes = Gson().fromJson(text, JsonObject::class.java)
        val zipCodeObject = zipCodes.get(zipCode)
        return (zipCodeObject as? JsonObject)?.let {
            LocationInfo(
                it.get("latitude").asString.toDouble(),
                it.get("longitude").asString.toDouble(),
                it.get("city").asString,
                it.get("stateId").asString
            )
        }
    }
}

data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val state: String
)
