package com.ssafy.ssaign.src.main.sign.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Sign")
data class Sign(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var point: List<Point> = listOf()
)
