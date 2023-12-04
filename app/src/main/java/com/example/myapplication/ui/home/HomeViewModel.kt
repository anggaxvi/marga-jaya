package com.example.myapplication.ui.home

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Repository

class HomeViewModel(private val appRepository: Repository):ViewModel() {
    fun getLapangan(tanggal:String) = appRepository.getLapangan(tanggal)
}