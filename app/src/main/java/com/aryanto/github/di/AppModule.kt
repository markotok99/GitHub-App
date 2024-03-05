package com.aryanto.github.di

import com.aryanto.github.data.remote.network.ApiClient
import com.aryanto.github.data.remote.network.ApiService
import org.koin.dsl.module
import retrofit2.create

val appModule = module {
//    single { ApiClient.getApiService() }
    single { ApiClient.retrofitInstance.create(ApiService::class.java) }
}