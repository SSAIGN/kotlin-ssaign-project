package com.ssafy.ssaign.src.main.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSettingsBinding
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import com.ssafy.ssaign.src.main.MainActivity

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::bind, R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    fun initEvent() {
        binding.fragmentSettingsIvBack.setOnClickListener {
            (context as MainActivity).finish()
        }

        binding.fragmentSettingsTvEditUser.setOnClickListener {
            (context as MainActivity).onChangeFragement(1)
        }

        binding.fragmentSettingsTvSuggestion.setOnClickListener {
            //(context as MainActivity).onChangeFragement(2)
            //프래그먼트 대신에 여기에 주은님이 원하시는 다이얼로그를 띄워주세요
        }

        binding.fragmentSettingsTvDeveloper.setOnClickListener {
            (context as MainActivity).onChangeFragement(3)
        }

        binding.fragmentSettingsTvVersion.setOnClickListener {
            (context as MainActivity).onChangeFragement(4)
        }

        binding.fragmentSettingsTvLicense.setOnClickListener {
            (context as MainActivity).onChangeFragement(5)
        }
    }
}