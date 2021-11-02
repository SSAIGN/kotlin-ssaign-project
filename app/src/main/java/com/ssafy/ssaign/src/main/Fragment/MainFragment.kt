package com.ssafy.ssaign.src.main.Fragment

import android.os.Bundle
import android.view.View
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentMainBinding
import com.ssafy.ssaign.src.main.MainActivity

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::bind, R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //시작 버튼을 눌렀을 때,
        binding.startBtn.setOnClickListener {
            //sharedpreferences를 사용하여 정보를 가져온다.

            //정보가 있다면 -> Viewmodel에 저장 -> MakeReportFramgent -> Report 생성

            //정보가 없다면 -> UserInfoFragment -> 정보 기입 -> Viewmodel에 저장 -> MakeReportFramgnet -> Report 생성

            //임시로 한 것
            (activity as MainActivity).onChangeFragement(1)
        }
    }
}