package com.ssafy.ssaign.src.main

import android.os.Bundle
import android.view.View
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding

// 서명 확인창
class SignConfirmFragment : BaseFragment<FragmentSignConfirmBinding>(FragmentSignConfirmBinding::bind, R.layout.fragment_sign_confirm) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun initView() {

    }

    fun initEvent() {
        binding.fragmentSignConfirmIvCancel.setOnClickListener {

        }

        binding.fragmentSignConfirmBtnSave.setOnClickListener {

        }
    }
}