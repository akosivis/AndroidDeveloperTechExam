package com.akosivis.androiddevelopertechnicalchallenge.database.converters

import androidx.room.TypeConverter

class ArrayListConverter {
    @TypeConverter
    fun listToString(list: ArrayList<String>): String {
        if (list.isEmpty()) {
            return ""
        } else {
            var itemsInStringForm = ""
            for (item in list) {
                itemsInStringForm += "$item,"
            }
            return itemsInStringForm
        }
    }

    @TypeConverter
    fun stringToList(string: String): ArrayList<String> {
        val listOfItems = ArrayList<String>()
        val items = string.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }
        for (s in items) {
            if (s.isNotEmpty()) {
                listOfItems.add(s)
            }
        }
        return listOfItems
    }
}