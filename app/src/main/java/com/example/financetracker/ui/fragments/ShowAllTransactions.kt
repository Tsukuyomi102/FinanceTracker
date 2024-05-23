package com.example.financetracker.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.R
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.FragmentShowAllTransactionsBinding
import com.example.financetracker.ui.adapters.CardTransactionAdapter
import com.example.financetracker.ui.adapters.CashTransactionAdapter
import com.example.financetracker.viewmodel.TransactionViewModel

class ShowAllTransactions : Fragment(), CardTransactionAdapter.OnCardTransactionClickListener, CashTransactionAdapter.OnCashTransactionClickListener {
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

        cardTransactionAdapter =
            CardTransactionAdapter(viewModel.transactionsList, requireContext(), true, this)
        cashTransactionAdapter =
            CashTransactionAdapter(viewModel.transactionsList, requireContext(), true, this)

        binding.imageBack.setOnClickListener() {
            findNavController().navigate(R.id.action_showAllTransactions_to_budgetFragment)
        }
        val showAllTransactionsFlag = arguments?.getBoolean("showAllTransactions") ?: false
        if (showAllTransactionsFlag) {
            binding.toolBarName.text = "Карточные операции"
            binding.recycleList.layoutManager = LinearLayoutManager(context)
            binding.recycleList.adapter = cardTransactionAdapter
        } else {
            binding.toolBarName.text = "Наличные операции"
            binding.recycleList.layoutManager = LinearLayoutManager(context)
            binding.recycleList.adapter = cashTransactionAdapter
        }
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    binding.clearText.visibility = View.VISIBLE
                    binding.clearText.setOnClickListener(){
                        binding.editSearch.text.clear()
                    }
                } else {
                    binding.clearText.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        return binding.root
    }
    override fun onCardTransactionClick(transaction: Transaction) {
        viewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_showAllTransactions_to_transactionDetailsFragment)
    }

    override fun onCashTransactionClick(transaction: Transaction) {
        viewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_showAllTransactions_to_transactionDetailsFragment)
    }
}