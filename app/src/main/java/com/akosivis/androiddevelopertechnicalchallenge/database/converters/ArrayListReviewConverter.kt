package com.akosivis.androiddevelopertechnicalchallenge.database.converters

import androidx.room.TypeConverter
import com.akosivis.androiddevelopertechnicalchallenge.database.Dimensions
import com.akosivis.androiddevelopertechnicalchallenge.database.Reviews
import com.google.gson.Gson

class ArrayListReviewConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun listToString(list: ArrayList<Reviews>): String {
        if (list.isEmpty()) {
            return ""
        } else {
            var itemsInStringForm = ""
            for (item in list) {
                val reviewItem = gson.toJson(item)
                itemsInStringForm += "$reviewItem,"
            }
            return itemsInStringForm
        }
    }

    @TypeConverter
    fun stringToList(string: String): ArrayList<Reviews> {
        val listOfItems = ArrayList<Reviews>()
        val items = string.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }
        for (s in items) {
            if (s.isNotEmpty()) {
                val review = gson.fromJson(s, Reviews::class.java)
                listOfItems.add(review)
            }
        }
        return listOfItems
    }
}