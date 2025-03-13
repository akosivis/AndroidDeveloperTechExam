package com.akosivis.androiddevelopertechnicalchallenge.database.converters

import androidx.room.TypeConverter
import com.akosivis.androiddevelopertechnicalchallenge.database.Meta
import com.akosivis.androiddevelopertechnicalchallenge.database.Reviews
import com.google.gson.Gson

class MetaConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromString(string: String): Meta {
        return gson.fromJson(string, Meta::class.java)
    }

    @TypeConverter
    fun toString(meta: Meta): String {
        return gson.toJson(meta)
    }
}