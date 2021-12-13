package com.ssafy.ssaign.config

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.ssafy.ssaign.src.main.sign.db.SignDatabase
import com.ssafy.ssaign.util.SharedPreferencesUtil


class ApplicationClass : Application() {

    // 코틀린의 전역변수 문법
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferencesUtil
        const val SHARED_PREFERENCES_NAME = "SSAFY_APP"

        // 싸인 저장 db
        lateinit var db: SignDatabase

        var dpHeight = 0.0F
        var dpWidth = 0.0F
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = SharedPreferencesUtil(applicationContext)
        db = SignDatabase.getInstance(applicationContext)!!
        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = resources.displayMetrics.density
        dpHeight = outMetrics.heightPixels / density
        dpWidth = outMetrics.widthPixels / density
    }
}