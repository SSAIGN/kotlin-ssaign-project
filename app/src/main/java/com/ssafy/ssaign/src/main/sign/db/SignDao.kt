package com.ssafy.ssaign.src.main.sign.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ssafy.ssaign.src.main.sign.model.Point
import com.ssafy.ssaign.src.main.sign.model.Sign

@Dao
interface SignDao {
    @Insert
    fun insertSign(sign: Sign)

    @Query("SELECT * FROM Sign WHERE id = :id")
    fun selectSign(id: String): Sign

    @Query("SELECT * FROM Sign")
    fun selectAllSign(): List<Sign>
}