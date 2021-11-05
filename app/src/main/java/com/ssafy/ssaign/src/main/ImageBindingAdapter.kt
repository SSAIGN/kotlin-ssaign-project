package com.ssafy.ssaign.src.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("bindImageUrl")
    fun bindImageUrl(view: ImageView, src: String){
        Glide.with(view.context)
            .load(src)
            .override(200, 200)
            .into(view)
    }
}