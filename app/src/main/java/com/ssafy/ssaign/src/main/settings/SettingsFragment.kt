package com.ssafy.ssaign.src.main.settings

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
import com.ssafy.ssaign.src.main.MainActivity.Companion.prefs
import java.lang.Exception
import kotlin.math.log

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::bind, R.layout.fragment_settings) {
    lateinit var dialog: Dialog
    lateinit var mDatabase: DatabaseReference
    lateinit var etSug: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirebase()
        initDialog()
        initEvent()
    }

    private fun initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    private fun writeSuggestion(user: User, sugContext: String) {
        mDatabase.child("suggestion").child(user.region).child(user.classNum).child(user.name).push().setValue(sugContext)
        etSug.setText("")
        showToastMessage("소중한 의견 감사합니다!")
    }

    private fun initDialog() {
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        dialog.setContentView(R.layout.dialog_suggestion)
        val params: ViewGroup.LayoutParams? = dialog.window!!.attributes
        val deviceWidth = size.x
        params!!.width = (deviceWidth * 0.9).toInt()
        dialog.window!!.attributes = params as WindowManager.LayoutParams

        etSug = dialog.findViewById(R.id.dialog_suggestion_etSug)
        dialog.findViewById<ImageView>(R.id.dialog_suggestion_ivCancel).setOnClickListener {
            etSug.setText("")
            dialog.hide()
        }

        dialog.findViewById<TextView>(R.id.dialog_suggestion_tvSend).setOnClickListener {
            val user = prefs.getUser()
            if (user != null) {
                writeSuggestion(user, etSug.text.toString())
            } else {
                showToastMessage("등록된 사용자 정보가 이상이 있습니다. 내 정보를 기입한 후 진행해주세요.")
            }
            dialog.hide()
        }

        var tvCount = dialog.findViewById<TextView>(R.id.dialog_suggestion_tvCount)
        etSug.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, count: Int) {
                var input = str ?: ""

                if (input.length <= 150) {
                    tvCount.text = "${input.length} / 150"
                } else {
                    etSug.setText(etSug.text.substring(0, 150))
                    etSug.setSelection(etSug.text.length)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }

    private fun initEvent() {
        binding.fragmentSettingsIvBack.setOnClickListener {
            (context as SettingsActivity).finish()
        }

        binding.fragmentSettingsTvEditUser.setOnClickListener {
            prefs.deleteUser()
            (context as SettingsActivity).finish()
        }

        binding.fragmentSettingsTvSuggestion.setOnClickListener {
            dialog.show()
        }

        binding.fragmentSettingsTvDeveloper.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SSAIGN/kotlin-ssaign-project")))
        }

        binding.fragmentSettingsTvLicense.setOnClickListener {
            (context as SettingsActivity).onChangeFragement(2)
        }
    }
}