package com.example.myapplication.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.di.Injection
import com.example.myapplication.ui.auth.LoginViewModel
import com.example.myapplication.ui.auth.RegisterViewModel
import com.example.myapplication.ui.detail.DatabaseViewModel
import com.example.myapplication.ui.detail.DetailViewModel
import com.example.myapplication.ui.home.HomeViewModel
import com.example.myapplication.ui.profile.ProfileViewModel

class Factory(private val context: Context):ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(Injection.apiService(context)) as T

        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(Injection.apiService(context)) as T

        }else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(Injection.apiService(context)) as T

        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(Injection.apiService(context)) as T

        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(Injection.apiService(context)) as T

        }
        throw IllegalArgumentException("Uknown ViewModel Class : ${modelClass.name}")
    }

}