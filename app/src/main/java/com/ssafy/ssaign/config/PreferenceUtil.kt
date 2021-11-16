package com.ssafy.ssaign.config

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

data class User(val name:String, val region:String, val classNum:String)

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("DB", Context.MODE_PRIVATE)

    fun getUser(): User? {
        val name = prefs.getString("name", "").toString()

        if(name == "")
            return null

        return User(prefs.getString("name", "").toString(), prefs.getString("region", "").toString(), prefs.getString("classNum", "").toString())
    }

    fun setUser(name: String, region: String, classNum: String) {
        val editor = prefs.edit()
        editor.putString("name", name)
        editor.putString("region", region)
        editor.putString("classNum", classNum)
        editor.commit()

        Log.d("SSAIGN-MainActivity", getUser().toString())
    }
}