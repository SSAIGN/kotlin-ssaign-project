package com.ssafy.ssaign.src.main.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.config.PreferenceUtil
import com.ssafy.ssaign.databinding.FragmentPermissionBinding
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.MainActivity.Companion.prefs
import com.ssafy.ssaign.src.main.UserInfoFragment
import com.ssafy.ssaign.src.main.report.MakeReportFragment

class PermissionFragment : BaseFragment<FragmentPermissionBinding>(FragmentPermissionBinding::bind, R.layout.fragment_permission) {
    var isPermissionAllowed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                isPermissionAllowed = isGranted
            }
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        permissionLauncher.launch(permission)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentPermissionBtnNext.setOnClickListener {
            if (isPermissionAllowed) {
                val user = prefs.getUser()

                //정보가 있다면 -> Viewmodel에 저장 -> MakeReportFramgent -> Report 생성
                if (user != null) {
                    (context as MainActivity).onChangeFragement(2)
                }

                //정보가 없다면 -> UserInfoFragment -> 정보 기입 -> Viewmodel에 저장 -> MakeReportFramgnet -> Report 생성
                else {
                    (context as MainActivity).onChangeFragement(1)
                }
            } else {
                showToastMessage("권한 허용이 필요합니다")
                // (context as MainActivity).finish()
            }
        }
    }
}