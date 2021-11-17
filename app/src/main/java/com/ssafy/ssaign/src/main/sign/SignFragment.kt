package com.ssafy.ssaign.src.main.sign

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.ApplicationClass.Companion.db
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSignBinding
import com.ssafy.ssaign.src.main.sign.db.SignDatabase
import com.ssafy.ssaign.src.main.sign.model.Sign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// 서명하는 창
class SignFragment : BaseFragment<FragmentSignBinding>(FragmentSignBinding::bind, R.layout.fragment_sign) {
    lateinit var draw: DrawSample


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 가로 모드
        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        draw = binding.draw

        binding.fragmentSignIvDelete.setOnClickListener {
            draw.reset()
        }

        binding.fragmentSignIvSave.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val job = CoroutineScope(Dispatchers.Default).async {
                    db.signDao().insertSign(Sign(0, draw.getSign()))
                }

                job.join()
                showToastMessage("저장 완료")
            }
        }
    }
}