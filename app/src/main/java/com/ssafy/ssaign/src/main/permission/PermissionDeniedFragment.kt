package com.ssafy.ssaign.src.main.permission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentPermissionDeniedBinding
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import com.ssafy.ssaign.src.main.MainActivity

class PermissionDeniedFragment : BaseFragment<FragmentPermissionDeniedBinding>(FragmentPermissionDeniedBinding::bind, R.layout.fragment_permission_denied) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentPermissionDeniedBtnBack.setOnClickListener {
            (context as MainActivity).finish()
        }
    }
}