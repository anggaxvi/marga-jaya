package com.example.myapplication.ui.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.History
import com.example.myapplication.data.database.HistoryRepository

class HistoryViewModel(application: Application):ViewModel() {
    private val historyRepository: HistoryRepository = HistoryRepository(application)

    fun getAllHistory():LiveData<List<History>> = historyRepository.getAllHistory()

    fun deleteHistory(history: History){
        historyRepository.deleteHistory(history)
    }
}