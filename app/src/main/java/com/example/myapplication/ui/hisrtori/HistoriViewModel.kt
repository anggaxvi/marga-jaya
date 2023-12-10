package com.example.myapplication.ui.hisrtori

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Repository

class HistoriViewModel(private val historiRepository: Repository):ViewModel() {

    fun getAllHistory() = historiRepository.getAllHistory()
}