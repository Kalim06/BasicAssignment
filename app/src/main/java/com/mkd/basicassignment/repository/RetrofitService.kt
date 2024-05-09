package com.mkd.basicassignment.repository


import com.mkd.basicassignment.model.VideoData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val supabaseRequestUrl = "https://ygiepjlzmizuqttakkhv.supabase.co/rest/v1/"
private const val supabaseApiKey =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlnaWVwamx6bWl6dXF0dGFra2h2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTQ5ODA2MDgsImV4cCI6MjAzMDU1NjYwOH0.Q86ksuPFyXeFYWYXFoe8Hyhbq7D3zzFOwlkXNB1j4kw"

interface RetrofitService {

    @GET("videos")
    suspend fun getSupabaseVideos(@Header("apikey") apikey: String = supabaseApiKey): Response<List<VideoData?>>

    companion object {
        private var retrofitService: RetrofitService? = null
        fun getSupabaseInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder().baseUrl(supabaseRequestUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}