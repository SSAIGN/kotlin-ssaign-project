package com.ssafy.ssaign.src.main

import android.os.Bundle
import android.view.View
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSignBinding

// 서명하는 창
class SignFragment : BaseFragment<FragmentSignBinding>(FragmentSignBinding::bind, R.layout.fragment_sign) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}