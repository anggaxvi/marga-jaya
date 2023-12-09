package com.example.myapplication.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.database.History
import com.example.myapplication.data.response.LapanganItem
import com.example.myapplication.databinding.ItemHistoryBinding
import com.example.myapplication.ui.home.HomeAdapter

class HistoryAdapter:ListAdapter<History,HistoryAdapter.HistoryHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickAdapter : OnItemClickAdapter

    fun setItemClickAdapter(onItemClickAdapter : OnItemClickAdapter){
        this.onItemClickAdapter = onItemClickAdapter
    }
    inner class HistoryHolder(val binding: ItemHistoryBinding):RecyclerView.ViewHolder(binding.root) {

        lateinit var getHistory : History
        fun bind(data:History){
            getHistory = data
            binding.tvJenlaphistory.text = data.jenisLap
            binding.tvJamhistory.text = data.jamLap
            binding.tvHarilap.text = data.hariLap
            binding.tvHaribooking.text = data.tglBooking

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
       val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val data = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClickAdapter.onItemClick(data)
        }
        holder.bind(data)
    }



    interface OnItemClickAdapter{
        fun onItemClick(data: History)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

        }
    }




}