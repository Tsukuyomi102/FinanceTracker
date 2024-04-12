package com.example.financetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentSingUpBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SingUpFragment : Fragment() {
    private lateinit var binding: FragmentSingUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingUpBinding.inflate(layoutInflater, container, false)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView?.visibility = View.GONE

        binding.signIn.setOnClickListener(){
            findNavController().navigate(R.id.action_singUpFragment_to_singInFragment)
        }

        return binding.root
    }
}