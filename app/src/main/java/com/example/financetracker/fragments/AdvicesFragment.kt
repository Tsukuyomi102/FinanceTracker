package com.example.financetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentAdvicesBinding

class AdvicesFragment : Fragment() {
    private lateinit var binding: FragmentAdvicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvicesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}