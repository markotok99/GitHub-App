package com.aryanto.github.data.remote.network

import com.aryanto.github.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val BASE_URL = BuildConfig.BASE_URL
    val API_TOKEN = BuildConfig.API_TOKEN

    fun getApiService(): ApiService{
        val interceptorLog = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        val interceptorAuth = Interceptor{
            val newRequest = it.request()
                .newBuilder()
                .addHeader("Authorization", API_TOKEN)
                .build()
            it.proceed(newRequest)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptorLog)
            .addInterceptor(interceptorAuth)
            .build()

        val retrofitInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitInstance.create(ApiService::class.java)
    }

}