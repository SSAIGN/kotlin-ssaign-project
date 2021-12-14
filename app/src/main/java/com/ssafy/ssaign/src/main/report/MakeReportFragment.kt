package com.ssafy.ssaign.src.main.report

import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ScrollView
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.ApplicationClass.Companion.db
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentMakeReportBinding
import com.ssafy.ssaign.src.main.Document
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.MainActivity.Companion.prefs
import com.ssafy.ssaign.src.main.MainActivity.Companion.viewModel
import com.ssafy.ssaign.src.main.sign.DrawSign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MakeReportFragment : BaseFragment<FragmentMakeReportBinding>(FragmentMakeReportBinding::bind, R.layout.fragment_make_report) {
    lateinit var draw: DrawSign
    var hasSign = false
    private val dataMonth = arrayOf("1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월")
    private val dataDay = arrayOf("1일","2일","3일","4일","5일","6일","7일","8일","9일","10일","11일","12일","13일","14일","15일","16일","17일","18일","19일","20일","21일","22일","23일","24일","25일","26일","27일","28일","29일","30일","31일")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initView()
        initData()
        initListener()
    }

    private fun initSign() {
        if(hasSign){
            CoroutineScope(Dispatchers.Main).launch {
                val sign = CoroutineScope(Dispatchers.Default).async {
                    db.signDao().selectSign("1")
                }.await()

                if(sign != null) {
                    draw.setSign(sign.point)
                }
            }
        }
    }

    private fun initSpinner() {
        val dayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dataDay)
        val monthAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dataMonth)

        binding.submitDay.adapter = dayAdapter
        binding.submitMonth.adapter = monthAdapter
        binding.eduMonth.adapter = monthAdapter
        binding.allClassCnt.adapter = dayAdapter
        binding.allStudyCnt.adapter = dayAdapter
    }

    private fun initView() {
        // 세로 모드
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        draw = binding.makeReportDraw
        draw.isValid = false
    }

    private fun showDialog()
    {
        val msgBuilder = AlertDialog.Builder(requireContext())
            .setTitle("서명하기")
            .setMessage("대표 서명이 이미 있습니다. 그래도 새롭게 서명을 하시겠습니까?")
            .setPositiveButton("확인") { p0, p1 ->
                savedoc()
                (context as MainActivity).onChangeFragement(3)
            }
            .setNegativeButton("취소") { p0, p1 ->
                showToastMessage("취소되었습니다.")
            }

        val msgDlg = msgBuilder.create()
        msgDlg.show()
    }

    private fun savedoc(){
        val month = binding.eduMonth.selectedItem.toString().replace("월","")
        val name = binding.nameTv.text.toString()
        val region = binding.regionTv.text.toString()
        val classNum = binding.classNumTv.text.toString()
        val totalAttendance = binding.allClassCnt.selectedItem.toString().replace("일","")
        val totalStudy = binding.allStudyCnt.selectedItem.toString().replace("일","")
        val submitMonth = binding.submitMonth.selectedItem.toString().replace("월","")
        val submitDay = binding.submitDay.selectedItem.toString().replace("일", "")
        viewModel.enteredDocument = Document(month, name, region, classNum, totalAttendance, totalStudy, submitMonth, submitDay)
    }

    private fun initListener() {
        binding.makeReportBtn.setOnClickListener {
            if(checkVaild()) {
                if (!hasSign){
                    binding.scrollview.fullScroll(ScrollView.FOCUS_DOWN)
                    showToastMessage("대표 서명이 없습니다. 서명을 해주세요.")
                }
                else {
                    savedoc()
                    (context as MainActivity).onChangeFragement(4)
                }
            }
            else showToastMessage("기입된 정보를 다시 확인해주세요.")
        }
        
        binding.signBtn.setOnClickListener {
            if (hasSign){
                if(checkVaild()){
                    showDialog()
                }else{
                    showToastMessage("모든 정보를 기입 후, 서명해주세요.")
                }
            }else{
                if(checkVaild()){
                    savedoc()
                    (context as MainActivity).onChangeFragement(3)
                }else{
                    showToastMessage("모든 정보를 기입 후, 서명해주세요.")
                }
            }
        }
    }

    private fun checkVaild() : Boolean{
        val month = binding.eduMonth.selectedItem.toString().replace("월","")
        val name = binding.nameTv.text.toString()
        val region = binding.regionTv.text.toString()
        val classNum = binding.classNumTv.text.toString()
        val totalAttendance = binding.allClassCnt.selectedItem.toString().replace("일","")
        val totalStudy = binding.allStudyCnt.selectedItem.toString().replace("일","")
        val submitMonth = binding.submitMonth.selectedItem.toString().replace("월","")
        val submitDay = binding.submitDay.selectedItem.toString().replace("일", "")

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

                var m = SimpleDateFormat("M").format(Date()).toInt() - 2
                if (m < 0) m = 11

                eduMonth.setSelection(m)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // 서명 데이터가 있는지 확인
        CoroutineScope(Dispatchers.Main).launch {
            val sign = CoroutineScope(Dispatchers.Default).async {
                db.signDao().selectSign("1")
            }.await()
            if (sign != null) {
                hasSign = true
                initSign()
            }
        }
    }
}