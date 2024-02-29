package com.aryanto.github.data.remote.network

import com.aryanto.github.data.model.Item
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getAllUsers(): List<Item>

}