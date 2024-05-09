package com.mkd.basicassignment.repository

enum class ApiStatus {
    SUCCESS, ERROR, LOADING
}

sealed class ApiResult<out T>(val status: ApiStatus, val data: T?, val message: String?) {

    data class Success<out R>(val resultData: R?) : ApiResult<R>(
        status = ApiStatus.SUCCESS, data = resultData, message = null
    )

    data class Error(val exception: String) : ApiResult<Nothing>(
        status = ApiStatus.ERROR, data = null, message = exception
    )

    data class Loading<out R>(val resultData: R?, val isLoading: Boolean) : ApiResult<R>(
        status = ApiStatus.LOADING, data = resultData, message = null
    )
}