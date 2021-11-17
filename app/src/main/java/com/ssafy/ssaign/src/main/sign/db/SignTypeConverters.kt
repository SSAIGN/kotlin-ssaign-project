package com.ssafy.ssaign.src.main.sign.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ssafy.ssaign.src.main.sign.model.Point
import com.ssafy.ssaign.src.main.sign.model.Sign

class SignTypeConverters {
    @TypeConverter
    fun PointToJson(value: List<Point>): String = Gson().toJson(value)

    @TypeConverter
    fun JsonToPoint(value: String) = Gson().fromJson(value, Array<Point>::class.java).toList()
}