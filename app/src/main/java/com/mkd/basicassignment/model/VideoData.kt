package com.mkd.basicassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoData(
    @SerializedName("ID") val id: String,
    @SerializedName("Channel Name") val channelName: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Likes") val likes: String,
    @SerializedName("Views") val views: String,
    @SerializedName("VideoUrl") val videoUrl: String,
    @SerializedName("ThumbnailUrl") val thumbnailUrl: String
) : Parcelable
