package com.ssafy.ssaign.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.ssafy.ssaign.config.BaseActivity
import com.ssafy.ssaign.R
import com.ssafy.ssaign.databinding.ActivitySplashBinding
import com.ssafy.ssaign.src.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val DELAY = 500 // 원하는 값으로 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 500)
    }
}