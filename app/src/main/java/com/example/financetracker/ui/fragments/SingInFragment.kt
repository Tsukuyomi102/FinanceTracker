package com.example.financetracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentSingInBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class SingInFragment : Fragment() {
    private lateinit var binding: FragmentSingInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingInBinding.inflate(layoutInflater, container,false)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView?.visibility = View.GONE

        binding.textViewReg.setOnClickListener(){
            findNavController().navigate(R.id.action_singInFragment_to_singUpFragment)
        }
        binding.btnLogin.setOnClickListener() {
            findNavController().navigate(R.id.action_singInFragment_to_dashboardFragment)
        }

        return binding.root
    }

}