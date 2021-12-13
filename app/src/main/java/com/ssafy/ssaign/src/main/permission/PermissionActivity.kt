package com.ssafy.ssaign.src.main.permission

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.ssaign.config.BaseActivity
import com.ssafy.ssaign.databinding.ActivityPermissionBinding
import com.ssafy.ssaign.src.main.MainActivity

class PermissionActivity : BaseActivity<ActivityPermissionBinding>(ActivityPermissionBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fragmentPermissionBtnNext.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}