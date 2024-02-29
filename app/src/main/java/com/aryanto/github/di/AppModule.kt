package com.aryanto.github.di

import com.aryanto.github.data.remote.network.ApiClient
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.getApiService() }
}