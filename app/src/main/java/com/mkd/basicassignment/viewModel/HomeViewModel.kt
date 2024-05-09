package com.mkd.basicassignment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkd.basicassignment.model.VideoData
import com.mkd.basicassignment.repository.ApiResult
import com.mkd.basicassignment.repository.RetrofitService
import com.mkd.basicassignment.repository.toResultFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var mLiveDataSupabaseVideo: MutableLiveData<ApiResult<List<VideoData?>>?> =
        MutableLiveData<ApiResult<List<VideoData?>>?>()

    fun getSupabaseVideos(): MutableLiveData<ApiResult<List<VideoData?>>?> {
        viewModelScope.launch {
            val flow = toResultFlow { RetrofitService.getSupabaseInstance().getSupabaseVideos() }
            flow.collect { result ->
                mLiveDataSupabaseVideo.value = result
            }
        }
        return mLiveDataSupabaseVideo
    }
}