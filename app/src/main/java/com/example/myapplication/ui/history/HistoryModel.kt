package com.example.myapplication.ui.history

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class HistoryModel(
    val id:Int = 0,
    val jenisLap : String,
    val jamLap : String,
    val hariLap : String,
    val tglBooking : String,
    val hargaLap : String
)
