package com.aryanto.github.data.remote.network

import com.aryanto.github.data.model.Item
import com.aryanto.github.data.model.ItemDetail
import com.aryanto.github.data.remote.reponse.ResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getAllUsers(): Response<List<Item>>

    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): Response<ResponseApi>

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): Response<ItemDetail>

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): Response<List<Item>>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): Response<List<Item>>

}