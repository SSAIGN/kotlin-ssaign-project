package com.ssafy.ssaign.src.main.report

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
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
    lateinit var dialog:Dialog

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

        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog01)

        dialog.findViewById<Button>(R.id.noBtn).setOnClickListener {
            (context as MainActivity).onChangeFragement(3)
            savedoc()
            dialog.hide()
        }

        dialog.findViewById<Button>(R.id.yesBtn).setOnClickListener {
            (context as MainActivity).onChangeFragement(4)
            savedoc()
            dialog.hide()
        }

        draw = dialog.findViewById(R.id.dialog_draw)
        draw.isValid = false
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
                    savedoc()
                    (context as MainActivity).onChangeFragement(4)
                }
                else {
                    dialog.show()
                }
            }
            else showToastMessage("기입된 정보를 다시 확인해주세요.")
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