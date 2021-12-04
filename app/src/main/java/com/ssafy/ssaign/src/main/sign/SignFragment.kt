package com.ssafy.ssaign.src.main.sign

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.ApplicationClass.Companion.db
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSignBinding
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.sign.model.Sign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

// 서명하는 창
class SignFragment : BaseFragment<FragmentSignBinding>(FragmentSignBinding::bind, R.layout.fragment_sign) {
    lateinit var draw: DrawSign

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
                (context as MainActivity).onChangeFragement(4)
            }
        }
    }
}