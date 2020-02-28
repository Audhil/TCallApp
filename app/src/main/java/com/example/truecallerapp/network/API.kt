package com.example.truecallerapp.network

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface API {
    @GET("/2018/01/22/life-as-an-android-engineer")
    fun grabBlog(): Flowable<ResponseBody>
}