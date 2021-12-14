package com.ssafy.ssaign.src.main.settings

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.databinding.FragmentSettingsBinding
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import com.ssafy.ssaign.src.main.MainActivity

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::bind, R.layout.fragment_settings) {
    lateinit var dialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()
        initEvent()
    }

    private fun initDialog() {
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_suggestion)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.findViewById<Button>(R.id.dialog_suggestion_btnCancel).setOnClickListener {

            dialog.hide()
        }

        dialog.findViewById<Button>(R.id.dialog_suggestion_btnSend).setOnClickListener {
            dialog.hide()
        }
    }

    private fun initEvent() {
        binding.fragmentSettingsIvBack.setOnClickListener {
            (context as MainActivity).finish()
        }

        binding.fragmentSettingsTvEditUser.setOnClickListener {
            (context as MainActivity).onChangeFragement(1)
        }

        binding.fragmentSettingsTvSuggestion.setOnClickListener {
            dialog.show()
        }

        binding.fragmentSettingsTvDeveloper.setOnClickListener {
            (context as MainActivity).onChangeFragement(3)
        }

        binding.fragmentSettingsTvVersion.setOnClickListener {
            (context as MainActivity).onChangeFragement(4)
        }

        binding.fragmentSettingsTvLicense.setOnClickListener {
            (context as MainActivity).onChangeFragement(5)
        }
    }
}