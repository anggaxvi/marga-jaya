package com.example.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Repository

class LoginViewModel(private val appRepository: Repository):ViewModel() {

    fun userLogin(email:String,password:String) = appRepository.userLogin(email, password)
}