package com.ssafy.ssaign.util

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.ssaign.config.ApplicationClass

class SharedPreferencesUtil(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
}