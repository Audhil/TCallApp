package com.example.truecallerapp.di.modules

import com.example.truecallerapp.network.API
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class APIModule {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    private val logInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                println("yup log: $message")
            }
        })
    }

    @Provides
    fun giveRetrofitAPIService(): API =
        Retrofit.Builder()
            .baseUrl("https://blog.truecaller.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(API::class.java)

}