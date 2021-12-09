package com.ssafy.ssaign.src.main.signconfirm

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import android.graphics.Bitmap

import android.os.Environment
import androidx.lifecycle.ViewModelProvider
import com.ssafy.ssaign.config.ApplicationClass.Companion.db
import com.ssafy.ssaign.src.main.sign.DrawSign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


// 서명 확인창
class SignConfirmFragment : BaseFragment<FragmentSignConfirmBinding>(FragmentSignConfirmBinding::bind, R.layout.fragment_sign_confirm) {
    lateinit var draw: DrawSign

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 세로 모드
        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        draw = binding.signInclude.includedSignDraw

        initView()
        initEvent()
    }

    fun initView() {
        CoroutineScope(Dispatchers.Main).launch {
            val sign = CoroutineScope(Dispatchers.Default).async {
                db.signDao().selectSign("8")
                //val list = db.signDao().selectAllSign()
                //list[list.lastIndex]
            }.await()

            if(sign != null) {
                draw.setSign(sign.point)
                //showToastMessage("싸인 불러오기 성공")
            } else {
                //showToastMessage("싸인 불러오기 실패")
            }
        }
    }

    fun initEvent() {
        binding.fragmentSignConfirmIvCancel.setOnClickListener {

        }

        binding.fragmentSignConfirmBtnSave.setOnClickListener {
            showToastMessage("캡쳐 성공")
            val sdf = SimpleDateFormat("yyyyMMddHHmmss") //년,월,일,시간 포멧 설정
            val time = Date() //파일명 중복 방지를 위해 사용될 현재시간
            val current_time = sdf.format(time) //String형 변수에 저장

            //Request_Capture(binding.testLayout, current_time + "_capture");
        }
    }

    // 특정 레이아웃 캡쳐해서 저장하기
    fun Request_Capture(view: View?, title: String) {
        if (view == null) { // Null Point Exception ERROR 방지
            println("::::ERROR:::: view == NULL")
            return
        }

        /* 캡쳐 파일 저장 */view.buildDrawingCache() //캐시 비트 맵 만들기
        val bitmap = view.drawingCache

        /* 저장할 폴더 Setting */
        val uploadFolder = Environment.getExternalStoragePublicDirectory("/DCIM/Camera/") //저장 경로 (File Type형 변수)
        if (!uploadFolder.exists()) { //만약 경로에 폴더가 없다면
            uploadFolder.mkdir() //폴더 생성
        }

        /* 파일 저장 */
        val Str_Path =
            Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera/" //저장 경로 (String Type 변수)
        try {
            val fos = FileOutputStream("$Str_Path$title.jpg") // 경로 + 제목 + .jpg로 FileOutputStream Setting
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}