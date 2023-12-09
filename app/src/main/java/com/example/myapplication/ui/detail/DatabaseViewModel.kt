package com.example.myapplication.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.History
import com.example.myapplication.data.database.HistoryRepository
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application) : ViewModel() {
    private val historyRepository: HistoryRepository = HistoryRepository(application)

    fun addHistory(history: History) {

        historyRepository.insertNewHistory(history)

    }

}