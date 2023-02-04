package com.example.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class TypeConverters {

    @TypeConverter
    fun listToJson(value: MutableList<String>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

}