package com.mkd.basicassignment.utils


import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mkd.basicassignment.model.VideoData
import com.mkd.basicassignment.R

@BindingAdapter("thumbnail")
fun ImageView.setThumbnail(video: VideoData?) {
    video?.let {

        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.error)

        Glide.with(this).load(it.thumbnailUrl).apply(options).into(this)
    }
}

@BindingAdapter("videoTitle")
fun TextView.setTitle(video: VideoData?) {
    video?.let {
        text = it.title
    }
}

@BindingAdapter("channelName")
fun TextView.setChannelName(video: VideoData?) {
    video?.let {
        text = it.channelName
    }
}

@BindingAdapter("videoInfo")
fun TextView.setVideoInfo(video: VideoData?) {
    video?.let {

        val info = "Likes - ${it.likes} \u2022 Views - ${it.views}"
        text = info
    }
}

@BindingAdapter("videoDescription")
fun TextView.setDescription(video: VideoData?) {
    video?.let {
        text = it.description
    }
}

