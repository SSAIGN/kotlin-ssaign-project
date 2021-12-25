package com.ssafy.ssaign.src.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.ssafy.ssaign.config.BaseActivity
import com.ssafy.ssaign.databinding.ActivitySplashBinding
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.permission.PermissionActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val DELAY = 1500L // 원하는 값으로 설정

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, PermissionActivity::class.java))
                finish()
            }
        }, DELAY)
    }
}