package com.example.myapplication.ui.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.FetchResult
import com.example.myapplication.data.pref.Preferences
import com.example.myapplication.data.response.DataLapangan
import com.example.myapplication.data.response.LapanganItem
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.Factory
import com.example.myapplication.ui.auth.LoginActivity
import com.example.myapplication.ui.detail.DeatailFragment
import com.example.myapplication.ui.detail.DetailActivity
import com.example.myapplication.ui.home.HomeAdapter
import com.example.myapplication.ui.home.HomeViewModel
import com.example.myapplication.ui.map.LocationActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var pref: Preferences
    private lateinit var recentDate: String
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var upDate: String
    private val calendar = Calendar.getInstance()
    private var pickerDate: String? = null
    private val homeViewModel: HomeViewModel by viewModels { Factory(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomefrag.layoutManager = layoutManager

        pref = Preferences(requireContext())

        if (pref.getToken() == null) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        } else {
          
            getDate()
            binding.btnDate.setOnClickListener {
                getDatePicker()
            }

            if (pickerDate != null) {
                upDate = pickerDate!!

            } else {
                upDate = recentDate
            }


            homeViewModel.getLapangan(upDate).observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it) {
                        is FetchResult.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is FetchResult.Success -> {
                            binding.progressBar.visibility = View.GONE
                            setData(it.data.data?.lapangan)

                        }

                        is FetchResult.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }


            binding.topBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.logout -> {
                        pref.clearToken()
                        pref.clearEmail()
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                        requireActivity().finish()
                        true
                    }

                    R.id.location -> {
                        startActivity(Intent(requireContext(), LocationActivity::class.java))
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


        return binding.root
    }


    private fun getDate() {
        //""dd,MMM yyyy"
        //dd-MM-yyyy
        //Sat Dec 02 2023

        val sformat = SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault())
        var date = Date()
        recentDate = sformat.format(date)


    }

    private fun getDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(), { DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedData = android.icu.util.Calendar.getInstance()
                selectedData.set(year, month, dayOfMonth)
                val dateFormate = SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault())
                val result = dateFormate.format(selectedData.time)
                pickerDate = result.toString()
//                binding.edtDatepicker.text = pickerDate.toString()

                homeViewModel.getLapangan(pickerDate.toString()).observe(viewLifecycleOwner) {
                    if (it != null) {
                        when (it) {
                            is FetchResult.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is FetchResult.Success -> {
                                binding.progressBar.visibility = View.GONE
                                setData(it.data.data?.lapangan)

                            }

                            is FetchResult.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }


            },
            calendar.get(android.icu.util.Calendar.YEAR),
            calendar.get(android.icu.util.Calendar.MONTH),
            calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()

    }

    fun setData(lapangan: List<LapanganItem?>?) {
        homeAdapter = HomeAdapter()
        homeAdapter.submitList(lapangan)
        binding.rvHomefrag.adapter = homeAdapter
        homeAdapter.setItemClickAdapter(object : HomeAdapter.OnItemClickAdapter {
            override fun onItemClick(data: LapanganItem) {
                val i = Intent(requireContext(), DetailActivity::class.java)
                i.putExtra(DetailActivity.EXTRA_ID, data.id)
                i.putExtra(DetailActivity.EXTRA_TGL, pickerDate)
                startActivity(i)


            }

        })
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
        const val EXTRA_NAME = "extra_name"
    }

}