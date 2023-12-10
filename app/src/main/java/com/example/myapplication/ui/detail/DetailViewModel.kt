package com.example.myapplication.ui.detail

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Repository

class DetailViewModel(private val appRepository : Repository):ViewModel() {

    fun getLapById(id:String,tanggal:String) = appRepository.getLapById(id,tanggal)

    fun payment(id_lap:String,tanggal: String) = appRepository.payment(id_lap,tanggal)
}