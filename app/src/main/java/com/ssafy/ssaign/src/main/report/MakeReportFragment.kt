package com.ssafy.ssaign.src.main.report

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.ApplicationClass.Companion.db
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentMakeReportBinding
import com.ssafy.ssaign.src.main.Document
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.MainActivity.Companion.prefs
import com.ssafy.ssaign.src.main.MainActivity.Companion.viewModel
import com.ssafy.ssaign.src.main.settings.SettingsActivity
import com.ssafy.ssaign.src.main.sign.DrawSign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MakeReportFragment : BaseFragment<FragmentMakeReportBinding>(FragmentMakeReportBinding::bind, R.layout.fragment_make_report) {
    private lateinit var draw: DrawSign
    private var hasSign = false
    private val dataMonth = arrayOf("1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월")
    private val dataDay = arrayOf("1일","2일","3일","4일","5일","6일","7일","8일","9일","10일","11일","12일","13일","14일","15일","16일","17일","18일","19일","20일","21일","22일","23일","24일","25일","26일","27일","28일","29일","30일","31일")
    private lateinit var signDialog:Dialog

    private lateinit var spinnerEduMonth:Spinner
    private lateinit var spinnerSubMonth:Spinner
    private lateinit var spinnerSubDay:Spinner
    private lateinit var spinnerRealAtt:Spinner
    private lateinit var spinnerAllAtt:Spinner
    private lateinit var nameTv:TextView
    private lateinit var regionTv:TextView
    private lateinit var classNumTv:TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initSpinner()
        initView()
        initData()
        initListener()
    }

    private fun initBinding() {
        spinnerEduMonth = binding.fragmentMakeReportSpinnerEduMonth
        spinnerSubMonth = binding.fragmentMakeReportSpinnerSubMonth
        spinnerSubDay = binding.fragmentMakeReportSubDay
        spinnerAllAtt = binding.fragmentMakeReportSpinnerAllAtt
        spinnerRealAtt = binding.fragmentMakeReportSpinnerRealAtt
        nameTv = binding.fragmentMakeReportTvName
        regionTv = binding.fragmentMakeReportTvRegion
        classNumTv = binding.fragmentMakeReportTvClassNum
    }

    private fun initSign() {
        if(hasSign){
            CoroutineScope(Dispatchers.Main).launch {
                val sign = withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                    db.signDao().selectSign("1")
                }

                if(sign != null) {
                    draw.setSign(sign.point)
                }
            }
        }
    }

    private fun initSpinner() {
        val dayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dataDay)
        val monthAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dataMonth)

        spinnerSubDay.adapter = dayAdapter
        spinnerSubMonth.adapter = monthAdapter
        spinnerEduMonth.adapter = monthAdapter
        spinnerRealAtt.adapter = dayAdapter
        spinnerAllAtt.adapter = dayAdapter
    }

    private fun initView() {
        // 세로 모드
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        signDialog = Dialog(requireContext())
        signDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        signDialog.setContentView(R.layout.dialog_sign)

        signDialog.findViewById<Button>(R.id.noBtn).setOnClickListener {
            (context as MainActivity).onChangeFragement(3)
            saveDoc()
            signDialog.hide()
        }

        signDialog.findViewById<Button>(R.id.yesBtn).setOnClickListener {
            (context as MainActivity).onChangeFragement(4)
            saveDoc()
            signDialog.hide()
        }

        draw = signDialog.findViewById(R.id.dialog_draw)
        draw.isValid = false
    }

    private fun saveDoc(){
        val name = nameTv.text.toString()
        val region = regionTv.text.toString()
        val classNum = classNumTv.text.toString()
        val month = spinnerEduMonth.selectedItem.toString().replace("월","")
        val totalAttendance = spinnerAllAtt.selectedItem.toString().replace("일","")
        val totalStudy = spinnerRealAtt.selectedItem.toString().replace("일","")
        val submitMonth = spinnerSubMonth.selectedItem.toString().replace("월","")
        val submitDay = spinnerSubDay.selectedItem.toString().replace("일", "")
        viewModel.enteredDocument = Document(month, name, region, classNum, totalAttendance, totalStudy, submitMonth, submitDay)
    }

    private fun initListener() {
        binding.makeReportBtn.setOnClickListener {
            if(checkVaild()) {
                if (!hasSign){
                    saveDoc()
                    (context as MainActivity).onChangeFragement(3)
                }
                else {
                    signDialog.show()
                }
            }
            else showToastMessage("기입된 정보를 다시 확인해주세요.")
        }

        binding.fragmentMakeReportIvSettings.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }
    }

    private fun checkVaild() : Boolean{
        val month = spinnerEduMonth.selectedItem.toString().replace("월","")
        val name = this.nameTv.text.toString()
        val region = regionTv.text.toString()
        val classNum = classNumTv.text.toString()
        val totalAttendance = spinnerAllAtt.selectedItem.toString().replace("일","")
        val totalStudy = spinnerRealAtt.selectedItem.toString().replace("일","")
        val submitMonth = spinnerSubMonth.selectedItem.toString().replace("월","")
        val submitDay = spinnerSubDay.selectedItem.toString().replace("일", "")

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
            nameTv.text = user.name
            regionTv.text = user.region
            classNumTv.text = user.classNum

            var m = SimpleDateFormat("M").format(Date()).toInt() - 2
            if (m < 0) m = 11

            spinnerEduMonth.setSelection(m)
        }
    }

    override fun onResume() {
        super.onResume()

        // 서명 데이터가 있는지 확인
        CoroutineScope(Dispatchers.Main).launch {
            val sign = withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                db.signDao().selectSign("1")
            }
            if (sign != null) {
                hasSign = true
                initSign()
            }
        }
    }
}
