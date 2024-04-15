package com.example.financetracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentAddExpenseBinding
import com.example.financetracker.databinding.FragmentAddIncomeBinding

class AddExpenseFragment : Fragment() {
    private lateinit var binding: FragmentAddExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExpenseBinding.inflate(layoutInflater, container, false)
        binding.textIncome.setOnClickListener() {
            findNavController().navigate(R.id.action_addExpenseFragment_to_addIncomeFragment)
        }
        return binding.root
    }
}