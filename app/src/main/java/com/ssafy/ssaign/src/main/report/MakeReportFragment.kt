package com.ssafy.ssaign.src.main.report

import android.os.Bundle
import android.view.View
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentMakeReportBinding
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.MainActivity.Companion.prefs
import java.text.SimpleDateFormat
import java.util.*

class MakeReportFragment : BaseFragment<FragmentMakeReportBinding>(FragmentMakeReportBinding::bind, R.layout.fragment_make_report) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initListener()
    }

    private fun initListener() {
        binding.makeSignBtn.setOnClickListener {
            if(checkVaild()) (context as MainActivity).onChangeFragement(3)
            else showToastMessage("기입된 정보를 다시 확인해주세요.")
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
    }
}