package com.ssafy.ssaign.src.main.signconfirm

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import android.graphics.Bitmap

import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ssafy.ssaign.config.ApplicationClass.Companion.db
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.MainActivity.Companion.viewModel
import com.ssafy.ssaign.src.main.sign.DrawSign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import com.ssafy.ssaign.src.main.MainViewModel

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

        draw = binding.fragmentSignConfirmDraw

        initView()
        initEvent()
    }

    fun initView() {
        CoroutineScope(Dispatchers.Main).launch {
            val sign = CoroutineScope(Dispatchers.Default).async {
                db.signDao().selectSign("1")
            }.await()

            if(sign != null) {
                draw.setSign(sign.point, "SignConfirmFragment")
            }
        }

        setUserInfo()
    }

    fun setUserInfo() {
        val time = Date() //파일명 중복 방지를 위해 사용될 현재시간

        val sdfYear = SimpleDateFormat("yyyy")
        val year = sdfYear.format(time)
        val document = viewModel.enteredDocument

        binding.apply {
            fragmentSignConfirmTvYear.text = year
            fragmentSignConfirmTvMonth1.text = document.month
            fragmentSignConfirmTvMonth2.text = document.month
            fragmentSignConfirmTvMonth3.text = document.month
            fragmentSignConfirmTvMonth4.text = document.subMonth
            fragmentSignConfirmTvDay.text = document.subDay
            fragmentSignConfirmTvCampus.text = document.campus
            fragmentSignConfirmTvClass.text = document.class_
            fragmentSignConfirmTvName1.text = document.name
            fragmentSignConfirmTvName2.text = document.name
            fragmentSignConfirmTvName3.text = document.name
            fragmentSignConfirmTvClassDay.text = document.classDay
            fragmentSignConfirmTvAttendDay.text = document.attendDay
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

            Request_Capture(binding.fragmentSignConfirmDocument, current_time + "_capture");
        }
    }

    // 특정 레이아웃 캡쳐해서 저장하기
    fun Request_Capture(view: View?, title: String) {
        if (view == null) { // Null Point Exception ERROR 방지
            println("::::ERROR:::: view == NULL")
            return
        }

        /* 캡쳐 파일 저장 */
        view.buildDrawingCache() //캐시 비트 맵 만들기
        val bitmap = view.drawingCache

        /* 저장할 폴더 Setting */
        val uploadFolder = Environment.getExternalStoragePublicDirectory("/Download/") //저장 경로 (File Type형 변수)
        if (!uploadFolder.exists()) { //만약 경로에 폴더가 없다면
            uploadFolder.mkdirs() //폴더 생성
        }

        /* 파일 저장 */
        val Str_Path = Environment.getExternalStorageDirectory().absolutePath + "/Download/" //저장 경로 (String Type 변수)
        Log.d("제발",Str_Path)
        try {
            val fos = FileOutputStream("$Str_Path$title.jpg") // 경로 + 제목 + .jpg로 FileOutputStream Setting
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos) // 파일 압축 (압축률 설정 1~100)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}