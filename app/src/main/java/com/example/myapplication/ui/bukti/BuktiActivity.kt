package com.example.myapplication.ui.bukti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBuktiBinding

class BuktiActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBuktiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuktiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jenisLap = intent.getStringExtra(EXTRA_JENIS)
        val hariLap = intent.getStringExtra(EXTRA_HARI)
        val sesiLap = intent.getStringExtra(EXTRA_SESI)
        val hargaLap = intent.getStringExtra(EXTRA_HARGA)
        val tglLap = intent.getStringExtra(EXTRA_TGL)

        binding.tvJenbukti.text = jenisLap.toString()
        binding.tvHaribukti.text = hariLap.toString()
        binding.tvJambukti.text = sesiLap.toString()
        binding.tvHargabukti.text = hargaLap.toString()
        binding.tvWaktupesanbukti.text = tglLap.toString()

    }

    companion object {
        var EXTRA_JENIS = "extra_jenislap"
        var EXTRA_HARI = "extra_harilap"
        var EXTRA_SESI = "extra_sesilap"
        var EXTRA_HARGA = "extra_hargalap"
        var EXTRA_TGL = "extra_tgllap"

    }
}