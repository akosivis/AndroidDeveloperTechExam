package com.akosivis.androiddevelopertechnicalchallenge.database.converters

import androidx.room.TypeConverter
import com.akosivis.androiddevelopertechnicalchallenge.database.Dimensions
import com.akosivis.androiddevelopertechnicalchallenge.database.Reviews
import com.google.gson.Gson

class ReviewConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromString(string: String): Reviews {
        return gson.fromJson(string, Reviews::class.java)
    }

    @TypeConverter
    fun toString(reviews: Reviews): String {
        return gson.toJson(reviews)
    }
}