package com.example.financetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentSingInBinding
import kotlin.math.log


class SingInFragment : Fragment() {
    private lateinit var binding: FragmentSingInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSingInBinding.inflate(layoutInflater, container,false)
        binding.textViewReg.setOnClickListener(){
            findNavController().navigate(R.id.action_singInFragment_to_singUpFragment)
        }

        return binding.root
    }

}