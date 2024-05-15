package com.example.financetracker.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.financetracker.databinding.FragmentDashboardBinding
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding

    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var cashViewModel: CashViewModel
    private lateinit var cardViewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)

        val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "")

        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)
        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)

        transactionViewModel.getTransactionsByEmail(email)
        cashViewModel.getCashByEmail(email)
        cardViewModel.getCardsByEmail(email)
        return binding.root
    }
}