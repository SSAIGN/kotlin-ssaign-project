package com.ssafy.ssaign.src.main.settings.version

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import com.ssafy.ssaign.databinding.FragmentVersionBinding
import com.ssafy.ssaign.src.main.settings.SettingsActivity

class VersionFragment : BaseFragment<FragmentVersionBinding>(FragmentVersionBinding::bind, R.layout.fragment_version) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var version = getAppVersion(requireContext())
        binding.fragmentVersionTvVersion.text = "현재버전 ${version}"

        // TODO 최신 버전 알아보는 방법 주은님꺼 보고 카피하기기
        //binding.fragmentVersionBtnUpdate.text

        binding.fragmentVersionIvBack.setOnClickListener {
            (context as SettingsActivity).finish()
        }
    }

    private fun getAppVersion(context: Context): String? {
        var versionName = ""
        try {
            val pm = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = pm.versionName
        } catch (e: Exception) {

        }
        return versionName
    }
}