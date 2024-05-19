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
import com.example.financetracker.viewmodel.TransactionViewModel

class TransactionListFragment : Fragment(), CardTransactionAdapter.OnCardTransactionClickListener,  CashTransactionAdapter.OnCashTransactionClickListener {
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

        val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "")

        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        viewModel.getTransactionsByEmail(email)
        cashTransactionAdapter = CashTransactionAdapter(viewModel.transactionsList, requireContext(), false, this)
        binding.recyclerCash.layoutManager = LinearLayoutManager(context)
        binding.recyclerCash.adapter = cashTransactionAdapter
        //Type mismatch: inferred type is TransactionListFragment but CardTransactionAdapter.onTransactionClickListener was expected
        cardTransactionAdapter = CardTransactionAdapter(viewModel.transactionsList, requireContext(), false, this)
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

    override fun onCardTransactionClick(transaction: Transaction) {
        viewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_budgetFragment_to_transactionDetailsFragment)
    }

    override fun onCashTransactionClick(transaction: Transaction) {
        viewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_budgetFragment_to_transactionDetailsFragment)
    }
}
