package com.aryanto.github.ui.activitys.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.github.data.model.Item
import com.aryanto.github.data.remote.network.ApiService
import com.aryanto.github.utils.MyStatement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeVM(
    private val apiService: ApiService
) : ViewModel() {

    private val _users = MutableLiveData<MyStatement<List<Item>>>()
    val users: LiveData<MyStatement<List<Item>>> = _users

    fun getUsers() {
        viewModelScope.launch {
            _users.postValue(MyStatement.Loading)
            try {
                val response = apiService.getAllUsers()

                if (response.isSuccessful) {
                    val items = response.body() ?: throw IllegalAccessException("Body is null")
                    _users.postValue(MyStatement.Success(items))

                } else {
                    throw IllegalAccessException("Data response fail")
                }
                Log.d("GH-HVM", "API Response: $response")
            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                _users.postValue(MyStatement.Error(errorMSG))
                Log.e("GH-HVM Error: ", errorMSG, e)
            }
        }
    }

}