package com.example.myapplication.data.database

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.detail.DatabaseViewModel
import com.example.myapplication.ui.history.HistoryViewModel

class DatabaseFactory private constructor(private val mApplication: Application):ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var INSTANCE:DatabaseFactory? = null

        @JvmStatic
        fun getInstance(application: Application):DatabaseFactory{
            if (INSTANCE == null){
                synchronized(DatabaseFactory::class.java){
                    INSTANCE = DatabaseFactory(application)
                }
            }
            return INSTANCE as DatabaseFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)){
            return DatabaseViewModel(mApplication) as T

        }else if (modelClass.isAssignableFrom(HistoryViewModel::class.java)){
            return HistoryViewModel(mApplication) as T
        }


        throw IllegalArgumentException("UKnown ViewModel class: ${modelClass.name}")
    }
}