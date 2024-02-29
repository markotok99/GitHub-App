package com.aryanto.github.repository

import com.aryanto.github.data.remote.network.ApiService

class AppRepository(
    private val apiService: ApiService
) {

    suspend fun getListUser() = apiService.getAllUsers()

}