package com.aryanto.github.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aryanto.github.data.model.Item
import com.aryanto.github.data.remote.network.ApiService
import com.aryanto.github.utils.MyStatement

class HomeVM(
    private val apiService: ApiService
) : ViewModel() {

    private val _users = MutableLiveData<MyStatement<List<Item>>>()
    val users: LiveData<MyStatement<List<Item>>> = _users

    suspend fun getUsers() {
        _users.postValue(MyStatement.Loading)
        try {
            val response = apiService.getAllUsers()
            if (response.isEmpty()) {
                throw IllegalAccessException("No data response")
            } else {
                _users.postValue(MyStatement.Success(response))
            }
        } catch (e: Exception) {
            val errorMSG = "${e.message}"
            _users.postValue(MyStatement.Error(errorMSG))
            Log.e("GH-HVM Get User Data: ", errorMSG, e)
        }
    }

}