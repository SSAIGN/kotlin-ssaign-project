package com.ssafy.ssaign.src.main.report

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.ApplicationClass.Companion.db
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentMakeReportBinding
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.MainActivity.Companion.prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MakeReportFragment : BaseFragment<FragmentMakeReportBinding>(FragmentMakeReportBinding::bind, R.layout.fragment_make_report) {

    var hasSign = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        // 세로 모드
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun showDialog()
    {
        val msgBuilder = AlertDialog.Builder(requireContext())
            .setTitle("서명하기")
            .setMessage("대표 서명이 이미 있습니다. 그래도 새롭게 서명을 하시겠습니까?")
            .setPositiveButton("확인") { p0, p1 ->
                (context as MainActivity).onChangeFragement(3)
            }
            .setNegativeButton("취소") { p0, p1 ->
                showToastMessage("취소되었습니다.")
            }

        val msgDlg = msgBuilder.create()
        msgDlg.show()
    }

    private fun initListener() {
        binding.makeReportBtn.setOnClickListener {
            if(checkVaild()) {
                if (!hasSign){
                    binding.scrollview.fullScroll(ScrollView.FOCUS_DOWN)
                    showToastMessage("대표 서명이 없습니다. 서명을 해주세요.")
                }
                else {
                    (context as MainActivity).onChangeFragement(3)
                }
            }
            else showToastMessage("기입된 정보를 다시 확인해주세요.")
        }
        
        binding.signBtn.setOnClickListener {
            if (hasSign){
                showDialog()
            }else{
                (context as MainActivity).onChangeFragement(3)
            }
        }
    }

    private fun checkVaild() : Boolean{
        val month = binding.monthTv.text.toString().trim()
        val name = binding.nameTv.text.toString().trim()
        val region = binding.regionTv.text.toString().trim()
        val classNum = binding.classNumTv.text.toString().trim()
        val totalAttendance = binding.attendanceTv.text.toString().trim()
        val totalStudy = binding.studyTv.text.toString().trim()
        val submitMonth = binding.submitMonthTv.text.toString().trim()
        val submitDay = binding.submitDayTv.text.toString().trim()

        val list = listOf(month, name, region, classNum, totalAttendance, totalStudy, submitMonth, submitDay)

        list.forEach{
            if(it == "") return false
        }

        return true
    }

    private fun initData() {
        // 유저 데이터를 읽어옴 -> null 이 아니라면 자동기입
        val user = prefs.getUser()
        if(user != null){
            with(binding) {
                nameTv.setText(user.name)
                regionTv.setText(user.region)
                classNumTv.setText(user.classNum)
                monthTv.setText((SimpleDateFormat("M").format(Date()).toInt()-1).toString())
            }
        }

        // 서명 데이터가 있는지 확인
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.Default).async {
                val list = db.signDao().selectAllSign()
                if (list.isNotEmpty()) hasSign = true
            }.await()
        }
    }

    override fun onResume() {
        super.onResume()

        // 서명 데이터가 있는지 확인
        CoroutineScope(Dispatchers.Main).launch {
            val sign = CoroutineScope(Dispatchers.Default).async {
                val list = db.signDao().selectAllSign()
                if (list.isNotEmpty()) hasSign = true
            }.await()
        }
    }
}