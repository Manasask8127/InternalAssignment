package com.example.manasask.assignment

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageBinding")
fun imageAdapter(image: ImageView, path: String) {

    Log.i("imagepath", path)
    Glide.with(image.context)
        .load(path)
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.noimage)
        .centerCrop()
        .into(image)

}