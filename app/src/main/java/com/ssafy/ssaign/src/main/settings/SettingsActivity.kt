package com.ssafy.ssaign.src.main.settings

import android.os.Bundle
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseActivity
import com.ssafy.ssaign.databinding.ActivitySettingsBinding
import com.ssafy.ssaign.src.main.settings.developer.DeveloperFragment
import com.ssafy.ssaign.src.main.settings.license.LicenseFragment
import com.ssafy.ssaign.src.main.settings.version.VersionFragment

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(ActivitySettingsBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //초기 실행화면 설정
        val currentFragment = supportFragmentManager.findFragmentById(R.id.activity_settings_fragment_container)

        if(currentFragment == null) {
            val fragment = SettingsFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_settings_fragment_container, fragment)
                .commit()
        }
    }

    fun onChangeFragement(s:Int) {
        when (s) {
            1 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_settings_fragment_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
            2 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_settings_fragment_container, DeveloperFragment())
                .addToBackStack(null)
                .commit()
            3 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_settings_fragment_container, VersionFragment())
                .addToBackStack(null)
                .commit()
            4 -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_settings_fragment_container, LicenseFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}