package com.example.myapplication.ui.hisrtori

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.database.History
import com.example.myapplication.data.response.BookingItem
import com.example.myapplication.databinding.ItemHistoriBinding
import com.example.myapplication.ui.history.HistoryAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoriAdapter:ListAdapter<BookingItem,HistoriAdapter.HistoriHolder> (DIFF_CALLBACK){

    private lateinit var onItemClickAdapter : OnItemClickAdapter

    fun setItemClickAdapter(onItemClickAdapter : OnItemClickAdapter){
        this.onItemClickAdapter = onItemClickAdapter
    }
    class HistoriHolder(val binding : ItemHistoriBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:BookingItem){

            val tgl = data.tanggal
            val sformat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val inputformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
            val date = inputformat.parse(tgl)
            val resultDate = sformat.format(date)

            binding.tvJenlaphistory.text = data.jenisLapangan
            binding.tvHarilap.text = resultDate
            binding.tvJamhistory.text = "${data.jamMulai} - ${data.jamBerakhir}"
            binding.tvStatusbooking.text = data.status


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriHolder {
       val binding = ItemHistoriBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoriHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoriHolder, position: Int) {
        val data = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClickAdapter.onItemClick(data)
        }
        holder.bind(data)
    }

    interface OnItemClickAdapter{
        fun onItemClick(data: BookingItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookingItem>() {
            override fun areItemsTheSame(oldItem: BookingItem, newItem: BookingItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BookingItem, newItem: BookingItem): Boolean {
                return oldItem == newItem
            }

        }
    }


}