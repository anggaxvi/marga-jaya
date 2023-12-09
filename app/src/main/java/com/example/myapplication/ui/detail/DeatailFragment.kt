package com.example.myapplication.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDeatailBinding


class DeatailFragment : Fragment() {
    private lateinit var binding : FragmentDeatailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDeatailBinding.inflate(inflater, container, false)


        return binding.root
    }

    companion object {
        var EXTRA_ID= "extra_name"
        var EXTRA_TGL = "extra_description"

    }
}