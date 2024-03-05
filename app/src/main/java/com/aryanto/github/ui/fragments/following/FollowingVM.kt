package com.aryanto.github.ui.fragments.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.github.data.model.Item
import com.aryanto.github.data.remote.network.ApiService
import com.aryanto.github.utils.MyStatement
import kotlinx.coroutines.launch

class FollowingVM(
    private val apiService: ApiService
) : ViewModel() {

    private val _following = MutableLiveData<MyStatement<List<Item>>>()
    val following: LiveData<MyStatement<List<Item>>> = _following

    fun fetchFollowing(username: String) {
        viewModelScope.launch {
            _following.postValue(MyStatement.Loading)

            try {
                val response = apiService.getFollowing(username)
                if (response.isSuccessful) {
                    val items = response.body() ?: throw IllegalAccessException("Body is null")
                    _following.postValue(MyStatement.Success(items))
                }

            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                _following.postValue(MyStatement.Error(errorMSG))
                Log.e("GH-FVM Get following: ", errorMSG, e)
            }
        }
    }

}