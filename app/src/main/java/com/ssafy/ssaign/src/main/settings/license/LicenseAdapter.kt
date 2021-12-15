package com.ssafy.ssaign.src.main.settings.license

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssaign.databinding.ListItemLicenseBinding

class LicenseAdapter(val licenseList: List<License>) : RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder>() {

    inner class LicenseViewHolder(private val binding: ListItemLicenseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(license: License) {
            binding.license = license
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseViewHolder {
        var listItemBinding = ListItemLicenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LicenseViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: LicenseViewHolder, position: Int) {
        holder.bindInfo(licenseList[position])
    }

    override fun getItemCount(): Int = licenseList.size
}