package com.example.myapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.response.ImageItem
import com.example.myapplication.data.response.LapanganItem
import com.example.myapplication.databinding.ItemHomeBinding

class HomeAdapter : ListAdapter<LapanganItem,HomeAdapter.HomeHolder>(DIFF_CALLBACK) {
    class HomeHolder(val binding : ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : LapanganItem){

            if (data.jenisLapangan?.jenisLapangan == "Lapangan 2"){
                Glide.with(binding.root.context)
                .load("https://ik.imagekit.io/khwldaurw/IMG-1701170162210_aQ3glrlSq.png")
                .centerCrop()
                .into(binding.ivHomefrag)
            }else{
                Glide.with(binding.root.context)
                    .load("https://ik.imagekit.io/khwldaurw/IMG-1700752848517_cJ5E-Znj3.jpg")
                    .centerCrop()
                    .into(binding.ivHomefrag)
            }
//            Glide.with(binding.root.context)
//                .load(data.jenisLapangan?.image?.get(1).toString())
//                .centerCrop()
//                .into(binding.ivHomefrag)
            binding.tvJenishomefrag.text = data.jenisLapangan?.jenisLapangan
            binding.tvMulaihomefrag.text = data.sesiLapangan?.jamMulai
            binding.tvAkhirhomefrag.text = data.sesiLapangan?.jamBerakhir
            binding.tvHargahomefrag.text = "Rp. ${data.harga}"
            binding.tvStatushomefrag.text = if (data.available == true) "TERSEDIA" else "TIDAK TERSEDIA"
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LapanganItem>() {
            override fun areItemsTheSame(oldItem: LapanganItem, newItem: LapanganItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LapanganItem, newItem: LapanganItem): Boolean {
                return oldItem == newItem
            }

        }
    }


}