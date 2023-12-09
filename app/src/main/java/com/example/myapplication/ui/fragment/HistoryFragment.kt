package com.example.myapplication.ui.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.database.DatabaseFactory
import com.example.myapplication.data.database.History
import com.example.myapplication.data.pref.Preferences
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.auth.LoginActivity
import com.example.myapplication.ui.bukti.BuktiActivity
import com.example.myapplication.ui.detail.DatabaseViewModel
import com.example.myapplication.ui.history.HistoryAdapter
import com.example.myapplication.ui.history.HistoryViewModel
import com.example.myapplication.ui.map.LocationActivity
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var pref: Preferences
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        historyViewModel = obtainViewModel(requireActivity())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.layoutManager = layoutManager

        initAction()

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

        historyViewModel.getAllHistory().observe(viewLifecycleOwner){
            setData(it)
        }


        return binding.root
    }

    private fun setData(data:List<History>){
        historyAdapter = HistoryAdapter()
        historyAdapter.submitList(data)
        binding.rvHistory.adapter = historyAdapter
        historyAdapter.setItemClickAdapter(object : HistoryAdapter.OnItemClickAdapter{
            override fun onItemClick(data: History) {
                val intent = Intent(requireContext(),BuktiActivity::class.java)
                intent.putExtra(BuktiActivity.EXTRA_JENIS,data.jenisLap)
                intent.putExtra(BuktiActivity.EXTRA_HARI,data.hariLap)
                intent.putExtra(BuktiActivity.EXTRA_SESI,data.jamLap)
                intent.putExtra(BuktiActivity.EXTRA_HARGA,data.hargaLap)
                intent.putExtra(BuktiActivity.EXTRA_TGL,data.tglBooking)
                startActivity(intent)
            }

        })


    }

    private fun initAction(){
        val itemTouchHelper = ItemTouchHelper(object :ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0,ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val history = (viewHolder as HistoryAdapter.HistoryHolder).getHistory
                historyViewModel.deleteHistory(history)
            }

        })
        itemTouchHelper.attachToRecyclerView(binding.rvHistory)
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

    private fun obtainViewModel(activity: FragmentActivity): HistoryViewModel {
        val factory = DatabaseFactory.getInstance(requireActivity().application)
        return ViewModelProvider(activity, factory).get(HistoryViewModel::class.java)
    }

}