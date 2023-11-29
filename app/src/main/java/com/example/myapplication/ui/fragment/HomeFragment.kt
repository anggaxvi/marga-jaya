package com.example.myapplication.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.data.pref.Preferences
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.auth.LoginActivity
import com.example.myapplication.ui.map.LocationActivity


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var pref: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        pref = Preferences(requireContext())

        if (pref.getToken() == null) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        } else {
            val email = pref.getEmail()
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Hallo")
                setMessage("${email}")
                setNegativeButton("Oke", null)
                create()
                show()
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

                    else -> false
                }
            }
        }
        return binding.root
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}