package com.example.myapplication.ui.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.myapplication.R
import com.example.myapplication.data.FetchResult
import com.example.myapplication.data.database.DatabaseFactory
import com.example.myapplication.data.database.History
import com.example.myapplication.data.pref.Preferences
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.ui.Factory
import com.example.myapplication.ui.auth.LoginActivity
import com.example.myapplication.ui.map.LocationActivity
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var databaseViewModel: DatabaseViewModel
    private lateinit var pref: Preferences
    private val detailViewModel: DetailViewModel by viewModels { Factory(this) }
    private var jenisLap: String? = null
    private var sesiLap: String? = null
    private var hargaLap: String? = null
    private var taglLap: String? = null
    private var descLap: String? = null
    private var tglBooking: String? = null
    private var idLapang: String? = null
    private var dateByStatus:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseViewModel = obtainViewModel(this@DetailActivity)
        getDate()

        val idLap = intent.getStringExtra(EXTRA_ID)
        val tglLap = intent.getStringExtra(EXTRA_TGL)

        if (tglLap == null){
            val sformat = SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault())
            var date = Date()
            dateByStatus = sformat.format(date)

        }else{
            dateByStatus = tglLap
        }


        binding.tglDetailfrag.text = tglLap.toString()
//        taglLap = tglLap.toString()

        detailViewModel.getLapById(idLap.toString(), dateByStatus.toString()).observe(this) {
            if (it != null) {
                when (it) {
                    is FetchResult.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }

                    is FetchResult.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = it.data?.data?.lapangan
                        if (data?.jenisLapangan?.jenisLapangan == "Lapangan Line 1") {
                            val imgSlider = binding.imgSlider
                            val slides = ArrayList<SlideModel>()
                            slides.add(SlideModel("https://ik.imagekit.io/khwldaurw/IMG-1701758155157_rgyPyItYs.png"))
                            slides.add(SlideModel("https://ik.imagekit.io/khwldaurw/IMG-1701757841475_OPxvpanho.png"))
                            imgSlider.setImageList(slides, ScaleTypes.CENTER_CROP)

//                            Glide.with(binding.root.context)
//                                .load("https://ik.imagekit.io/khwldaurw/IMG-1701758155157_rgyPyItYs.png")
//                                .centerCrop()
//                                .into(binding.ivDetailfrag)
//
                        } else if (data?.jenisLapangan?.jenisLapangan == "Lapangan Line 2") {

                            val imgSlider = binding.imgSlider
                            val slides = ArrayList<SlideModel>()
                            slides.add(SlideModel("https://ik.imagekit.io/khwldaurw/IMG-1701757882694_o8BbOwSUD.png"))
                            slides.add(SlideModel("https://ik.imagekit.io/khwldaurw/IMG-1701757901492_2zgc4RXbd.png"))
                            imgSlider.setImageList(slides, ScaleTypes.CENTER_CROP)

//                            Glide.with(binding.root.context)
//                                .load("https://ik.imagekit.io/khwldaurw/IMG-1701757882694_o8BbOwSUD.png")
//                                .centerCrop()
//                                .into(binding.ivDetailfrag)
//
                        } else if (data?.jenisLapangan?.jenisLapangan == "Lapangan Line 3") {
                            val imgSlider = binding.imgSlider
                            val slides = ArrayList<SlideModel>()
                            slides.add(SlideModel("https://ik.imagekit.io/khwldaurw/IMG-1701757929432_rohOBTtK9.png"))
                            slides.add(SlideModel("https://ik.imagekit.io/khwldaurw/IMG-1701757971133_EN8qeoo-K.png"))
                            imgSlider.setImageList(slides, ScaleTypes.CENTER_CROP)

//                            Glide.with(binding.root.context)
//                                .load("https://ik.imagekit.io/khwldaurw/IMG-1701757929432_rohOBTtK9.png")
//                                .centerCrop()
//                                .into(binding.ivDetailfrag)
//
                        } else {
                            val imgSlider = binding.imgSlider
                            val slides = ArrayList<SlideModel>()
                            slides.add(SlideModel("https://ik.imagekit.io/khwldaurw/IMG-1701758012101_V3hpzRcso.png"))
                            slides.add(SlideModel("https://ik.imagekit.io/khwldaurw/IMG-1701758036000_pI3loOpXf.png"))
                            imgSlider.setImageList(slides, ScaleTypes.CENTER_CROP)

//                            Glide.with(binding.root.context)
//                                .load("https://ik.imagekit.io/khwldaurw/IMG-1701758012101_V3hpzRcso.png")
//                                .centerCrop()
//                                .into(binding.ivDetailfrag)
                        }




                        binding.tvStatusdetailfrag.text = if (data?.available == true) "TERSEDIA" else "TIDAK TERSEDIA"
                        binding.tvJenisdeatailfrag.text = data?.jenisLapangan?.jenisLapangan
                        binding.tvDescdetailfrag.text = data?.jenisLapangan?.deskripsi
                        binding.tvHargadetailfrag.text = "Rp.${data?.harga}/sesi"
                        binding.tvJamdetailfrag.text =
                            "${data?.sesiLapangan?.jamMulai} - ${data?.sesiLapangan?.jamBerakhir}"

                        jenisLap = data?.jenisLapangan?.jenisLapangan
                        descLap = data?.jenisLapangan?.deskripsi
                        hargaLap = "Rp.${data?.harga}"
                        sesiLap = "${data?.sesiLapangan?.jamMulai} - ${data?.sesiLapangan?.jamBerakhir}"
                        idLapang = data?.id


                        binding.btnPesan.isEnabled = data?.available == true

                    }

                    is FetchResult.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        binding.btnPesan.setOnClickListener {
//            val data = History(
//                jenisLap = jenisLap.toString(),
//                jamLap = sesiLap.toString(),
//                hariLap = taglLap.toString(),
//                tglBooking = tglBooking.toString(),
//                hargaLap = hargaLap.toString()
//            )
//            databaseViewModel.addHistory(data)

            detailViewModel.payment(idLapang.toString(),dateByStatus.toString()).observe(this){
                if (it != null){
                    when(it){
                        is FetchResult.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is FetchResult.Success ->{
                            binding.progressBar.visibility = View.GONE
                            intentMidtrans(it.data.data?.redirectUrl.toString())
                            Toast.makeText(this, "Berhasil melakukan booking lapangan", Toast.LENGTH_SHORT).show()

                        }

                        is FetchResult.Error ->{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Gagal dalam melakukan booking lapangan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }



        }

        binding.topBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    pref.clearToken()
                    pref.clearEmail()
                    startActivity(Intent(this@DetailActivity, LoginActivity::class.java))
                    finish()
                    true
                }

                R.id.location -> {
                    startActivity(Intent(this@DetailActivity, LocationActivity::class.java))
                    true
                }

                R.id.admin -> {
                    val nomorWA = "+6285173209431"
                    val pesanWa =
                        "Hallo min,maaf mengganggu waktunya saya ingin merubah jadwal booking saya.Terima Kasih :)"
                    aapInstallOrNot(nomorWA, pesanWa)

                    true
                }

                else -> false
            }
        }


    }

    private fun obtainViewModel(activity: AppCompatActivity): DatabaseViewModel {
        val factory = DatabaseFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DatabaseViewModel::class.java)
    }

    private fun intentMidtrans(link:String){

//        val url = "${link}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${link}"))
        startActivity(intent)
    }

    private fun getDate() {
        //""dd,MMM yyyy"
        //dd-MM-yyyy
        //Sat Dec 02 2023
        //EEE MMM dd yyyy"

        val sformat = SimpleDateFormat("dd MMM yyyy,HH:mm", Locale.getDefault())
        var date = Date()
        tglBooking = sformat.format(date)


    }

    private fun aapInstallOrNot(nomor: String, pesan: String) {
//        val uri = Uri.parse("smsto:$nomor")
//        val intent = Intent(Intent.ACTION_SENDTO,uri)
//        intent.setPackage("com.whatsapp")

        val url = "https://api.whatsapp.com/send?phone=$nomor\"+\"&text=$pesan"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))

//        intent.putExtra(Intent.EXTRA_TEXT,pesan)


        try {
            startActivity(intent)

        } catch (e: Exception) {
            val playStoreIntent = Intent(Intent.ACTION_VIEW)
            playStoreIntent.data = Uri.parse("market://details?id=com.whatsapp")
            startActivity(playStoreIntent)
        }

    }

    companion object {
        var EXTRA_ID = "extra_id"
        var EXTRA_TGL = "extra_tgl"
        var EXTRA_STATUS = "extra_status"

    }
}