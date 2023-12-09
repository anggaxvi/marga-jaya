package com.example.myapplication.data.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(application: Application) {
    private val historyDao:HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryDatabase.getDatabase(application)
        historyDao = db.historiDao()
    }

    fun getAllHistory():LiveData<List<History>> = historyDao.getAllHistory()


   fun insertNewHistory(newHistory: History){
       executorService.execute { historyDao.insertHistory(newHistory) }

    }

    fun deleteHistory(history: History){
        executorService.execute { historyDao.deleteHistory(history) }

    }
}