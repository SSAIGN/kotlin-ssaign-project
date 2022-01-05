package com.ssafy.ssaign.src.main.signconfirm

import android.Manifest
import android.content.ContentValues
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build

import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import com.ssafy.ssaign.config.ApplicationClass.Companion.db
import com.ssafy.ssaign.src.main.Document
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.MainActivity.Companion.viewModel
import com.ssafy.ssaign.src.main.sign.DrawSign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

// 서명 확인창
class SignConfirmFragment : BaseFragment<FragmentSignConfirmBinding>(FragmentSignConfirmBinding::bind, R.layout.fragment_sign_confirm) {
    lateinit var draw: DrawSign
    lateinit var document: Document

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
        document = viewModel.enteredDocument

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
            (context as MainActivity).onChangeFragement(2)
        }

        binding.fragmentSignConfirmBtnSave.setOnClickListener {
            val sdf = SimpleDateFormat("yyyyMMddHHmmss") //년,월,일,시간 포멧 설정
            val time = Date() //파일명 중복 방지를 위해 사용될 현재시간
            val current_time = sdf.format(time) //String형 변수에 저장
            //Request_Capture(binding.fragmentSignConfirmDocument, current_time + "_capture");

            Request_Capture(binding.fragmentSignConfirmDocument, "${document.campus}_${document.class_}반_${document.name}");
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

        var fos: OutputStream? = null

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context?.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, "$title.png")
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, "$title.png")
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            showToastMessage("사진이 저장되었습니다.")
        }
    }
}