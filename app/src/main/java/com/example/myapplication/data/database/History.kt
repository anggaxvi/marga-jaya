package com.example.myapplication.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class History(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id:Int = 0,

    @ColumnInfo("jenis_lapangan")
    val jenisLap : String,

    @ColumnInfo("jam_lapangan")
    val jamLap : String,

    @ColumnInfo("hari_lapangan")
    val hariLap : String,

    @ColumnInfo("tgl_booking")
    val tglBooking : String,

    @ColumnInfo("harga_lapangan")
    val hargaLap : String
)
