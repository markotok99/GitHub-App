package com.aryanto.github.ui.fragments.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.github.data.model.Item
import com.aryanto.github.data.remote.network.ApiService
import com.aryanto.github.utils.MyStatement
import kotlinx.coroutines.launch

class FollowersVM(
    private val apiService: ApiService
) : ViewModel() {
    private val _followers = MutableLiveData<MyStatement<List<Item>>>()
    val followers: LiveData<MyStatement<List<Item>>> = _followers

    fun fetchFollowers(username: String) {
        viewModelScope.launch {
            _followers.postValue(MyStatement.Loading)

            try {
                val response = apiService.getFollowers(username)
                if (response.isSuccessful) {
                    val items = response.body() ?: throw IllegalAccessException("Body is null")
                    _followers.postValue(MyStatement.Success(items))
                }

            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                _followers.postValue(MyStatement.Error(errorMSG))
                Log.e("GH-FVM Get followers: ", errorMSG, e)
            }
        }
    }
}