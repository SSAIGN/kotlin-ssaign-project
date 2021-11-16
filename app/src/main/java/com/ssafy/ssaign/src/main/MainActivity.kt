package com.ssafy.ssaign.src.main

import android.os.Bundle
import android.util.Log
import com.ssafy.ssaign.config.BaseActivity
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.PreferenceUtil
import com.ssafy.ssaign.databinding.ActivityMainBinding
import com.ssafy.ssaign.src.main.report.MakeReportFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //초기 실행화면 설정
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        //sharedpreferences를 사용하여 정보를 가져온다.
        prefs = PreferenceUtil(this)
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
    }

    fun onChangeFragement(s:Int){
        when(s) {
            1 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, UserInfoFragment())
                .commit()

            2-> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MakeReportFragment())
                .commit()

            3-> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, SignFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }

    companion object {
        lateinit var prefs: PreferenceUtil
    }
}