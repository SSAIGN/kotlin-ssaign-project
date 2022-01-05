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
            ),
            License(
                "Jsoup",
                "https://github.com/jhy/jsoup",
                "(C) 2009 Jonathan Hedley",
                "MIT License"
            ),
            License(
                "Glide",
                "https://github.com/bumptech/glide",
                "(C) 2014 Google",
                "BSD, part MIT and Apache 2.0"
            ),
            License(
                "Lottie Signature Animation",
                "https://assets4.lottiefiles.com/packages/lf20_9dmzmwdm.json",
                "(C) 2021 Antoine Wentzler",
                "Apache License 2.0"
            ),
            License(
                "함초롬체",
                "https://www.hancom.com/cs_center/csDownload.do",
                "(C) 2010. Hancom",
                "https://info.kcopa.or.kr/license/747"
            ),
        )
    }
}