package com.akosivis.androiddevelopertechnicalchallenge.database.converters

import androidx.room.TypeConverter
import com.akosivis.androiddevelopertechnicalchallenge.database.Dimensions
import com.google.gson.Gson

class DimensionsConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromString(string: String): Dimensions {
        return gson.fromJson(string, Dimensions::class.java)
    }

    @TypeConverter
    fun toString(dimensions: Dimensions): String {
        return gson.toJson(dimensions)
    }
}