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

class TransactionListFragment : Fragment(), CardTransactionAdapter.OnCardTransactionClickListener,  CashTransactionAdapter.OnCashTransactionClickListener {
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
    ): View? {
        binding = FragmentTransactionListBinding.inflate(layoutInflater, container, false)

        val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "")

        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)

        transactionViewModel.getTransactionsByEmail(email)
        cardViewModel.getCardsByEmail(email)
        cashViewModel.getCashByEmail(email)

        cashTransactionAdapter = CashTransactionAdapter(transactionViewModel.transactionsList, requireContext(), false, this)
        binding.recyclerCash.layoutManager = LinearLayoutManager(context)
        binding.recyclerCash.adapter = cashTransactionAdapter

        cardTransactionAdapter = CardTransactionAdapter(transactionViewModel.transactionsList, requireContext(), false, this)
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
        transactionViewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_budgetFragment_to_transactionDetailsFragment)
    }

    override fun onCashTransactionClick(transaction: Transaction) {
        transactionViewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_budgetFragment_to_transactionDetailsFragment)
    }
}
