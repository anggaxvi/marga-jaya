package com.example.myapplication.ui.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.data.pref.Preferences
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.auth.LoginActivity
import com.example.myapplication.ui.map.LocationActivity
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var pref: Preferences
    private val calendar = Calendar.getInstance()
    private lateinit var tvDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        pref = Preferences(requireContext())

        if (pref.getToken() == null) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        } else {

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

                    else -> false
                }
            }

//            val tvDate : TextView = binding.tvDate
//            val btnCalendar: ImageView = binding.addDate

//            btnCalendar.setOnClickListener {
//                showDatePicker()
//
//            }
        }

        return binding.root
    }


//    private fun showDatePicker() {
//        val datePickerDialog = DatePickerDialog(
//            requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
//                val selectedData = Calendar.getInstance()
//                selectedData.set(year, monthOfYear, dayOfMonth)
//                val dateFormate = SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault())
//                val result = dateFormate.format(selectedData.time)
//                tvDate = binding.tvDate
//                tvDate.text = result.toString()
//
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
//        datePickerDialog.show()
//    }

}