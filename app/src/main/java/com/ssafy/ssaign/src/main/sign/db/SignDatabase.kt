package com.ssafy.ssaign.src.main.sign.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ssafy.ssaign.src.main.sign.model.Point
import com.ssafy.ssaign.src.main.sign.model.Sign

@Database(entities = [Sign::class], version = 1)
@TypeConverters(SignTypeConverters::class)
abstract class SignDatabase: RoomDatabase() {
    abstract fun signDao(): SignDao

    companion object {
        private var instance: SignDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SignDatabase? {
            if(instance == null) {
                synchronized(SignDatabase::class){
                    instance = Room.databaseBuilder(
                        context,
                        SignDatabase::class.java,
                        "sign-database"
                    ).build()
                }
            }
            return instance
        }
    }
}