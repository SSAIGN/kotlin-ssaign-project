package com.ssafy.ssaign.src.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.ssafy.ssaign.config.BaseActivity
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.PreferenceUtil
import com.ssafy.ssaign.databinding.ActivityMainBinding
import com.ssafy.ssaign.src.main.permission.PermissionDeniedFragment
import com.ssafy.ssaign.src.main.report.MakeReportFragment
import com.ssafy.ssaign.src.main.sign.SignFragment
import com.ssafy.ssaign.src.main.signconfirm.SignConfirmFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel 설정
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        prefs = PreferenceUtil(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            initFragmentSetting()
        } else {
            val permissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                    if(isGranted) {
                        initFragmentSetting()
                    } else {
                        showToastMessage("권한 허용이 필요합니다")
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, PermissionDeniedFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                }
            val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
            permissionLauncher.launch(permission)
        }
    }

    fun initFragmentSetting() {
        //초기 실행화면 설정
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        //sharedpreferences를 사용하여 정보를 가져온다.
        val user = prefs.getUser()
        Log.d("SSAIGN-MainActivity", user.toString())

        //정보가 있다면 -> Viewmodel에 저장 -> MakeReportFramgent -> Report 생성
        if(user != null){
            if(currentFragment == null){
                val fragment = MakeReportFragment()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit()
            }
        }

        //정보가 없다면 -> UserInfoFragment -> 정보 기입 -> Viewmodel에 저장 -> MakeReportFramgnet -> Report 생성
        else{
            onChangeFragement(1)
        }
    }

    fun onChangeFragement(s:Int){
        when(s) {
            1 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, UserInfoFragment())
                .commit()
            2 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MakeReportFragment())
                .commit()
            3 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SignFragment())
                .addToBackStack(null)
                .commit()
            4 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SignConfirmFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var viewModel: MainViewModel
    }
}