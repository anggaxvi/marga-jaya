package com.example.myapplication.data

sealed class FetchResult<out R> private constructor(){
    data class Success<out T>(val data : T):FetchResult<T>()
    data class Error(val error : String) : FetchResult<Nothing>()
    object Loading : FetchResult<Nothing>()
}