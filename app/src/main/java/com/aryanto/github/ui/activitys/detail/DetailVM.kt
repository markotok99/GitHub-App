package com.aryanto.github.ui.activitys.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.github.data.model.ItemDetail
import com.aryanto.github.data.remote.network.ApiService
import com.aryanto.github.utils.MyStatement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailVM(
    private val apiService: ApiService
) : ViewModel() {

    private val _detailUser = MutableLiveData<MyStatement<List<ItemDetail>>>()
    val detailUser: LiveData<MyStatement<List<ItemDetail>>> = _detailUser

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            _detailUser.postValue(MyStatement.Loading)
            try {
                val response = apiService.getDetailUser(username)

                if (response.isSuccessful) {
                    response.body()?.let { item ->
                        _detailUser.postValue(MyStatement.Success(listOf(item)))
                    } ?: throw IllegalAccessException("Body is null")
                } else {
                    throw IllegalAccessException("${response.body()?.toString()}")
                }
                Log.d("GH-VM", "API Response: $response")
            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                _detailUser.postValue(MyStatement.Error(errorMSG))
                Log.e("GH-DVM Get Detail User: ", errorMSG, e)
            }
        }
    }

}