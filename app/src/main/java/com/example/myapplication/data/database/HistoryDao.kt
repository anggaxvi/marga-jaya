package com.example.myapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Dao
interface HistoryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(histori:History)

    @Query("SELECT * FROM history ORDER BY tgl_booking DESC")
    fun getAllHistory():LiveData<List<History>>
    @Delete
     fun deleteHistory(histori: History)
}