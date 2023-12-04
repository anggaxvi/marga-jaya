package com.example.myapplication.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.FetchResult
import com.example.myapplication.data.pref.Preferences
import com.example.myapplication.data.response.ProfileResponse
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.ui.Factory
import com.example.myapplication.ui.auth.LoginActivity
import com.example.myapplication.ui.map.LocationActivity
import com.example.myapplication.ui.profile.ProfileViewModel


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var pref : Preferences
    private val profileViewModel : ProfileViewModel by viewModels {Factory(requireContext())  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        pref = Preferences(requireContext())

        if (pref.getToken() == null) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        } else {

            profileViewModel.getUserProfile().observe(viewLifecycleOwner){
                if (it != null){
                    when(it){
                        is FetchResult.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is FetchResult.Success ->{
                            binding.progressBar.visibility = View.GONE
                            setDataProfile(it.data)
                        }

                        is FetchResult.Error ->{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(),"Pengambilan data gagal",Toast.LENGTH_SHORT).show()
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

                    else -> false
                }
            }


            binding.btnLogout.setOnClickListener {
                pref.clearToken()
                startActivity(Intent(requireContext(),LoginActivity::class.java))
            }
        }

        return binding.root
    }


    private fun setDataProfile(data:ProfileResponse){


//        var name = binding.nameProfile.text
//        var email = binding.emailProfile.text
//        var telp = binding.telpProfile.text
//        var role = binding.roleProfile.text

        val name = data.data?.user?.name.toString()
        val email = data.data?.user?.email.toString()
        val telp = data.data?.user?.noTelp.toString()
//        val role =data.data?.user?.role.toString()

        binding.nameProfile.text = name
        binding.emailProfile.text = email
        binding.telpProfile.text = telp
//        binding.roleProfile.text = role

    }

}