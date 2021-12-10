package com.ssafy.ssaign.src.main

import android.os.Bundle
import android.view.View
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentUserInfoBinding
import com.ssafy.ssaign.src.main.MainActivity.Companion.prefs

class UserInfoFragment : BaseFragment<FragmentUserInfoBinding>(FragmentUserInfoBinding::bind, R.layout.fragment_user_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBtn.setOnClickListener {
            if(checkValid()) (context as MainActivity).onChangeFragement(2)
            else showToastMessage("입력값이 유효하지 않습니다. 다시 입력해주세요.")
        }
    }

    fun checkValid() : Boolean {
        val name = binding.userNameEt.text.toString().trim()
        val region = binding.userLocationEt.text.toString().trim()
        val classNum = binding.userClassEt.text.toString().trim()

        // 입력값 유효성 검사 -> TODO 작성한 내용 한번 더 띄워주고 맞는지 확인
        // TODO classNum을 드랍메뉴로 바꿀지?
        if(name != "" && classNum != "" && region != "" ){
            prefs.setUser(name, region, classNum)
            showToastMessage("성공적으로 저장 되었습니다!")
            return true
        }
        return false
    }

}