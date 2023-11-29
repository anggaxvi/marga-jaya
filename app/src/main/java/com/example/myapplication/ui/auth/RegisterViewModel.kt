package com.example.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Repository

class RegisterViewModel(private val appRepository: Repository):ViewModel() {

    fun userRegister(name : String,email : String,password : String,no_telp : String,confirm_password : String) =
            appRepository.userRegister(name, email, password, no_telp, confirm_password)

}