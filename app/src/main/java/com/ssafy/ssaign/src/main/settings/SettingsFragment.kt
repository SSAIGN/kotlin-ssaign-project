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
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.commit
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ssafy.ssaign.R
import com.ssafy.ssaign.config.BaseFragment
import com.ssafy.ssaign.config.User
import com.ssafy.ssaign.databinding.FragmentSettingsBinding
import com.ssafy.ssaign.databinding.FragmentSignConfirmBinding
import com.ssafy.ssaign.src.main.MainActivity
import com.ssafy.ssaign.src.main.MainActivity.Companion.prefs
import java.lang.Exception

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::bind, R.layout.fragment_settings) {
    lateinit var dialog: Dialog
    lateinit var mDatabase: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirebase()
        initDialog()
        initEvent()
    }

    private fun initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    private fun writeSuggestion(user: User, sugContext:String){
        mDatabase.child("suggestion").child(user.region).child(user.classNum).child(user.name).push().setValue(sugContext)
        dialog.findViewById<TextView>(R.id.dialog_suggestion_tvSug).text = ""
        showToastMessage("소중한 의견 감사합니다.")
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
            val sugContext = dialog.findViewById<TextView>(R.id.dialog_suggestion_tvSug).text.toString()
            val user = prefs.getUser()
            if (user != null) {
                writeSuggestion(user, sugContext)
            }else{
                showToastMessage("등록된 사용자 정보가 이상이 있습니다. 내 정보를 기입한 후 진행해주세요.")
            }
            dialog.hide()
        }
    }

    private fun initEvent() {
        binding.fragmentSettingsIvBack.setOnClickListener {
            (context as SettingsActivity).finish()
        }

        binding.fragmentSettingsTvEditUser.setOnClickListener {
            (context as SettingsActivity).finish()
        }

        binding.fragmentSettingsTvSuggestion.setOnClickListener {
            dialog.show()
        }

        binding.fragmentSettingsTvDeveloper.setOnClickListener {
            (context as SettingsActivity).onChangeFragement(2)
        }

        binding.fragmentSettingsTvVersion.setOnClickListener {
            (context as SettingsActivity).onChangeFragement(3)
        }

        binding.fragmentSettingsTvLicense.setOnClickListener {
            (context as SettingsActivity).onChangeFragement(4)
        }
    }
}