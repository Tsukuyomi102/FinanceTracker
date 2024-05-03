package com.example.financetracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentShowAllTransactionsBinding
import com.example.financetracker.ui.adapters.CardTransactionAdapter
import com.example.financetracker.ui.adapters.CashTransactionAdapter
import com.example.financetracker.viewmodel.TransactionViewModel

class ShowAllTransactions : Fragment() {
    private lateinit var binding: FragmentShowAllTransactionsBinding

    private lateinit var viewModel: TransactionViewModel

    private lateinit var cashTransactionAdapter: CashTransactionAdapter
    private lateinit var cardTransactionAdapter: CardTransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowAllTransactionsBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)

        cardTransactionAdapter = CardTransactionAdapter(viewModel.transactionsList, requireContext(), true)
        cashTransactionAdapter = CashTransactionAdapter(viewModel.transactionsList, requireContext(), true)

        binding.imageBack.setOnClickListener(){
            findNavController().navigate(R.id.action_showAllTransactions_to_budgetFragment)
        }
        val showAllTransactionsFlag = arguments?.getBoolean("showAllTransactions") ?: false
        if (showAllTransactionsFlag){
            binding.toolBarName.text = "Карточные операции"
            binding.recycleList.layoutManager = LinearLayoutManager(context)
            binding.recycleList.adapter = cardTransactionAdapter
        } else {
            binding.toolBarName.text = "Наличные операции"
            binding.recycleList.layoutManager = LinearLayoutManager(context)
            binding.recycleList.adapter = cashTransactionAdapter
        }
        return binding.root
    }

}