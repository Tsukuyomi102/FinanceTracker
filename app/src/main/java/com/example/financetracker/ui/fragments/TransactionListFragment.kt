package com.example.financetracker.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.R
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.FragmentTransactionListBinding
import com.example.financetracker.ui.adapters.CardTransactionAdapter
import com.example.financetracker.ui.adapters.CashTransactionAdapter
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel

class TransactionListFragment : Fragment(), CardTransactionAdapter.OnCardTransactionClickListener, CashTransactionAdapter.OnCashTransactionClickListener {
    private lateinit var binding: FragmentTransactionListBinding

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var cardViewModel: CardViewModel
    private lateinit var cashViewModel: CashViewModel

    private lateinit var cashTransactionAdapter: CashTransactionAdapter
    private lateinit var cardTransactionAdapter: CardTransactionAdapter

    private var showAllCardTransactions = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionListBinding.inflate(inflater, container, false)

        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)

        setupClickListeners()
        observeTransactions()

        return binding.root
    }

    private fun setupClickListeners() {
        binding.cardShowAll.setOnClickListener {
            showAllCardTransactions = true
            navigateToShowAllTransactions()
        }

        binding.cashShowAll.setOnClickListener {
            showAllCardTransactions = false
            navigateToShowAllTransactions()
        }
    }

    private fun navigateToShowAllTransactions() {
        val bundle = Bundle().apply {
            putBoolean("showAllTransactions", showAllCardTransactions)
        }
        findNavController().navigate(R.id.action_budgetFragment_to_showAllTransactions, bundle)
    }

    private fun observeTransactions() {
        // Наблюдение за изменениями в списке транзакций
        transactionViewModel.getTransactionLiveData().observe(viewLifecycleOwner) { transactions ->
            updateCardTransactions(transactions)
            updateCashTransactions(transactions)
        }
    }

    private fun updateCardTransactions(transactions: List<Transaction>) {
        cardTransactionAdapter = CardTransactionAdapter(transactions, requireContext(), false, this)
        binding.recyclerCard.layoutManager = LinearLayoutManager(context)
        binding.recyclerCard.adapter = cardTransactionAdapter
    }

    private fun updateCashTransactions(transactions: List<Transaction>) {
        cashTransactionAdapter = CashTransactionAdapter(transactions, requireContext(), false, this)
        binding.recyclerCash.layoutManager = LinearLayoutManager(context)
        binding.recyclerCash.adapter = cashTransactionAdapter
    }

    override fun onCardTransactionClick(transaction: Transaction) {
        transactionViewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_budgetFragment_to_transactionDetailsFragment)
    }

    override fun onCashTransactionClick(transaction: Transaction) {
        transactionViewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_budgetFragment_to_transactionDetailsFragment)
    }
}