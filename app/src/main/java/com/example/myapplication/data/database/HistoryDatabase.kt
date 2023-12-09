package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [History::class],
    version = 1,
    exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historiDao() : HistoryDao

    companion object{
        @Volatile
        private var INSTANCE : HistoryDatabase? = null

        @JvmStatic
        fun getDatabase(conntext:Context):HistoryDatabase{
            if (INSTANCE == null){
                synchronized(HistoryDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(conntext.applicationContext,
                        HistoryDatabase::class.java,"history_database")
                        .build()
                }
            }
            return INSTANCE as HistoryDatabase
        }
    }
}