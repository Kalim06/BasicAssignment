package com.mkd.basicassignment.adapters

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.mkd.basicassignment.R
import com.mkd.basicassignment.databinding.VideoItemCardBinding
import com.mkd.basicassignment.model.VideoData

class VideoItemAdapter : RecyclerView.Adapter<VideoItemAdapter.ViewHolder>() {

    private var data = listOf<VideoData>()
    private var currentPlayingPosition: Int? = null // Track currently playing position
    private var currentVideoView: VideoView? = null // Track currently playing video view

    internal fun setData(list: List<VideoData>) {
        this.data = list
        notifyDataSetChanged() // Notify adapter that dataset has changed
    }

    class ViewHolder private constructor(
        private val binding: VideoItemCardBinding, private val adapter: VideoItemAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(video: VideoData, position: Int) {
            with(binding) {
                videoData = video
                executePendingBindings()

                if (adapter.currentPlayingPosition == position) {
                    // If this is the currently playing video, show VideoView and hide thumbnail
                    videoThumbnail.visibility = View.GONE
                    playBtn.visibility = View.GONE
                    videoViewContainer.visibility = View.VISIBLE
                    videoProgressBar.visibility = View.VISIBLE

                    // Set current video view
                    adapter.currentVideoView = videoView

                    // Extract the video URL from the VideoData object
                    val uri = Uri.parse(video.videoUrl)

                    videoView.setVideoURI(uri)

                    // Create MediaController
                    val mediaController = MediaController(this.root.context)
                    mediaController.setAnchorView(videoView)
                    videoView.setMediaController(mediaController)

                    videoView.setOnCompletionListener {
                        videoProgressBar.visibility = View.GONE
                    }

                    videoView.setOnErrorListener { _: MediaPlayer?, _: Int, _: Int ->
                        videoProgressBar.visibility = View.GONE
                        true
                    }

                    videoView.setOnPreparedListener {
                        videoView.start()
                        videoProgressBar.visibility = View.GONE
                    }

                } else {
                    // If this is not the currently playing video, hide VideoView and show thumbnail
                    videoThumbnail.visibility = View.VISIBLE
                    playBtn.visibility = View.VISIBLE
                    videoViewContainer.visibility = View.GONE
                    videoProgressBar.visibility = View.GONE
                }

                showVideoInfo.setOnClickListener {
                    val newVisibility =
                        if (videoInfoContainer.visibility == View.GONE) View.VISIBLE else View.GONE
                    videoInfoContainer.visibility = newVisibility

                    val newArrowResource =
                        if (newVisibility == View.VISIBLE) R.drawable.up_arrow else R.drawable.down_arrow
                    showVideoInfo.setImageResource(newArrowResource)
                }

                playBtn.setOnClickListener {

                    adapter.currentPlayingPosition?.let { currentPosition ->
                        adapter.notifyItemChanged(currentPosition)
                    }

                    adapter.stopPlayback()

                    // Start playback of the clicked video
                    adapter.currentPlayingPosition = position
                    adapter.notifyItemChanged(position)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup, adapter: VideoItemAdapter): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VideoItemCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, adapter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position)
    }

    override fun getItemCount() = data.size

    private fun stopPlayback() {
        currentVideoView?.stopPlayback()
    }
}
