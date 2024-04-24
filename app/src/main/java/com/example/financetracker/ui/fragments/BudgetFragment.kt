package com.example.financetracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {
    private lateinit var binding: FragmentBudgetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetBinding.inflate(layoutInflater, container, false)
        binding.operations.setOnClickListener() {
            changeColor(binding.operations, binding.aims)
        }
        binding.aims.setOnClickListener() {
            changeColor(binding.aims, binding.operations)
        }
        return binding.root
    }

    private fun changeColor(firstButton: TextView, secondButton: TextView) {
        firstButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.buttonColor))
        firstButton.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.white))
        secondButton.setBackgroundResource(R.drawable.rounded_border)
        secondButton.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.buttonColor))
    }
}