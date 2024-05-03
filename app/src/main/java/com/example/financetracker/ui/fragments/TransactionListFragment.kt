package com.example.financetracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentTransactionListBinding
import com.example.financetracker.ui.adapters.CardTransactionAdapter
import com.example.financetracker.ui.adapters.CashTransactionAdapter
import com.example.financetracker.viewmodel.TransactionViewModel

class TransactionListFragment : Fragment() {
    private lateinit var binding: FragmentTransactionListBinding

    private lateinit var viewModel: TransactionViewModel

    private lateinit var cashTransactionAdapter: CashTransactionAdapter
    private lateinit var cardTransactionAdapter: CardTransactionAdapter

    private var showAllCardTransactions = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionListBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        cashTransactionAdapter = CashTransactionAdapter(viewModel.transactionsList, requireContext(), false)
        binding.recyclerCash.layoutManager = LinearLayoutManager(context)
        binding.recyclerCash.adapter = cashTransactionAdapter

        cardTransactionAdapter = CardTransactionAdapter(viewModel.transactionsList, requireContext(), false)
        binding.recyclerCard.layoutManager = LinearLayoutManager(context)
        binding.recyclerCard.adapter = cardTransactionAdapter

        binding.cardShowAll.setOnClickListener(){
            showAllCardTransactions = true
            val bundle = Bundle()
            bundle.putBoolean("showAllTransactions", showAllCardTransactions)
            findNavController().navigate(R.id.action_budgetFragment_to_showAllTransactions, bundle)
        }

        binding.cashShowAll.setOnClickListener(){
            showAllCardTransactions = false
            val bundle = Bundle()
            bundle.putBoolean("showAllTransactions", showAllCardTransactions)
            findNavController().navigate(R.id.action_budgetFragment_to_showAllTransactions, bundle)
        }
        return binding.root
    }
}
