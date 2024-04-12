package com.example.financetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentAddIncomeBinding

class AddIncomeFragment : Fragment() {
    private lateinit var binding: FragmentAddIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(layoutInflater, container, false)
        binding.textExpense.setOnClickListener() {
            findNavController().navigate(R.id.action_addIncomeFragment_to_addExpenseFragment)
        }
        return binding.root
    }
}