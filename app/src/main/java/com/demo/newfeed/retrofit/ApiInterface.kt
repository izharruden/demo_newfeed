package com.demo.newfeed.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api/v2/posts")
    fun get_post_card(): Call<ResponseBody>
}