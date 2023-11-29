package com.example.myapplication.ui.profile

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Repository

class ProfileViewModel(private val appRepository: Repository):ViewModel() {

    fun getUserProfile() = appRepository.userProfile()
}