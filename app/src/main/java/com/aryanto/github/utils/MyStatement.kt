package com.aryanto.github.utils

sealed class MyStatement<out T> {
    data class Success<out T>(val data: T): MyStatement<T>()
    data class Error(val error: String): MyStatement<Nothing>()
    data object Loading : MyStatement<Nothing>()
}