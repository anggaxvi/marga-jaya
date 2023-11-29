package com.example.myapplication.data.di

import android.content.Context
import com.example.myapplication.data.Repository
import com.example.myapplication.data.api.ApiConfig
import com.example.myapplication.data.pref.Preferences
import kotlinx.coroutines.runBlocking

object Injection {
    fun apiService(context: Context):Repository{
        val pref = Preferences(context)
        val user = runBlocking { pref.getToken().toString() }
        val serviceApi = ApiConfig.getApiService(user)
        return Repository(serviceApi)
    }
}