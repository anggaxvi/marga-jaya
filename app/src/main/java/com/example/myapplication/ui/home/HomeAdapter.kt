package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.response.ImageItem
import com.example.myapplication.data.response.LapanganItem
import com.example.myapplication.databinding.ItemHomeBinding
import com.example.myapplication.ui.detail.DeatailFragment

class HomeAdapter : ListAdapter<LapanganItem,HomeAdapter.HomeHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickAdapter : OnItemClickAdapter

    fun setItemClickAdapter(onItemClickAdapter : OnItemClickAdapter){
        this.onItemClickAdapter = onItemClickAdapter
    }
    class HomeHolder(val binding : ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : LapanganItem) {

            if (data.jenisLapangan?.jenisLapangan == "Lapangan Line 1"){
                Glide.with(binding.root.context)
                .load("https://ik.imagekit.io/khwldaurw/IMG-1701758155157_rgyPyItYs.png")
                .centerCrop()
                .into(binding.ivHomefrag)

            }else if(data.jenisLapangan?.jenisLapangan == "Lapangan Line 2"){
                Glide.with(binding.root.context)
                    .load("https://ik.imagekit.io/khwldaurw/IMG-1701757882694_o8BbOwSUD.png")
                    .centerCrop()
                    .into(binding.ivHomefrag)

            }else if (data.jenisLapangan?.jenisLapangan == "Lapangan Line 3") {
                Glide.with(binding.root.context)
                    .load("https://ik.imagekit.io/khwldaurw/IMG-1701757929432_rohOBTtK9.png")
                    .centerCrop()
                    .into(binding.ivHomefrag)
//
            }else{
                Glide.with(binding.root.context)
                    .load("https://ik.imagekit.io/khwldaurw/IMG-1701758012101_V3hpzRcso.png")
                    .centerCrop()
                    .into(binding.ivHomefrag)
            }
//            var img = data.jenisLapangan?.image?.get(0)
//            Glide.with(binding.root.context)
//                .load(img.toString())
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
        holder.binding.btnDetailHome.setOnClickListener {
            onItemClickAdapter.onItemClick(data)

        }
        holder.bind(data)
    }

    interface OnItemClickAdapter{
        fun onItemClick(datta: LapanganItem)
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