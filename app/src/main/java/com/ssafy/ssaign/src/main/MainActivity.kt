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
import com.ssafy.ssaign.src.main.permission.PermissionFragment
import com.ssafy.ssaign.src.main.report.MakeReportFragment
import com.ssafy.ssaign.src.main.sign.SignFragment
import com.ssafy.ssaign.src.main.signconfirm.SignConfirmFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @RequiresApi(Build.VERSION_CODES.M) // TODO 마시멜로우 이하 버전에서는 문제가 있을수도 있긴한데... 솔까.. 마시멜로우를 아직도 쓰는건...좀...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel 설정
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //초기 실행화면 설정
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        prefs = PreferenceUtil(this)

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
                if(currentFragment == null){
                    val fragment = UserInfoFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container,fragment)
                        .commit()
                }
            }
        } else {
            if(currentFragment == null){
                val fragment = PermissionFragment()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit()
            }
        }
    }

    fun onChangeFragement(s:Int){
        when(s) {
            1 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, UserInfoFragment())
                .addToBackStack(null)
                .commit()
            2 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MakeReportFragment())
                .addToBackStack(null)
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
            5 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, PermissionFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var viewModel: MainViewModel
    }
}