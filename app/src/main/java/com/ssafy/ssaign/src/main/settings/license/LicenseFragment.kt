package com.ssafy.ssaign.src.main.settings.license

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentLicenseBinding
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import com.ssafy.ssaign.src.main.settings.SettingsActivity

class LicenseFragment : BaseFragment<FragmentLicenseBinding>(FragmentLicenseBinding::bind, R.layout.fragment_license) {
    lateinit var licenseAdapter: LicenseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
    }

    fun initView() {
        licenseAdapter = LicenseAdapter(getLicenseList())

        binding.fragmentLicenseRvLicense.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = licenseAdapter
        }
    }

    fun initEvent() {
        binding.fragmentLicenseIvBack.setOnClickListener {
            (context as SettingsActivity).finish()
        }
    }

    fun getLicenseList(): List<License> {
        return listOf(
            License(
                "Lottie for Android",
                "https://github.com/airbnb/lottie-android",
                "(C) 2017. Airbnb",
                "Apache License 2.0"
            )
        )
    }
}