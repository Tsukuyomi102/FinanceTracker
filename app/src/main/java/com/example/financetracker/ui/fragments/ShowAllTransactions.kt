package com.example.financetracker.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private lateinit var allTransactionsList: List<Transaction>
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowAllTransactionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        allTransactionsList = viewModel.transactionsList.toList()
        handler = Handler(Looper.getMainLooper())

        val showAllTransactionsFlag = arguments?.getBoolean("showAllTransactions") ?: false
        if (showAllTransactionsFlag) {
            showTransactions(true)
        } else {
            showTransactions(false)
        }

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_showAllTransactions_to_budgetFragment)
        }

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().toLowerCase()
                binding.clearText.visibility = View.VISIBLE
                binding.clearText.setOnClickListener(){
                    binding.editSearch.text.clear()
                }
                onSearchQueryChanged(searchText, showAllTransactionsFlag)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.placeHolderButton.setOnClickListener {
            val searchText = binding.editSearch.text.toString().trim().toLowerCase()
            filterTransactions(searchText, showAllTransactionsFlag)
        }

        binding.imageSearch.setOnClickListener {
            val searchText = binding.editSearch.text.toString().trim().toLowerCase()
            filterTransactions(searchText, showAllTransactionsFlag)
        }
        return binding.root
    }

    private fun showTransactions(showCardTransactions: Boolean) {
        val toolbarName = if (showCardTransactions) "Карточные операции" else "Наличные операции"
        binding.toolBarName.text = toolbarName
        binding.recycleList.layoutManager = LinearLayoutManager(context)
        val filteredTransactionsList = allTransactionsList.filter { it.isCreditCard == showCardTransactions }
        val adapter = if (showCardTransactions) CardTransactionAdapter(filteredTransactionsList, requireContext(), true, this)
        else CashTransactionAdapter(filteredTransactionsList, requireContext(), true, this)
        binding.recycleList.adapter = adapter
    }

    private fun filterTransactions(searchText: String, showCardTransactions: Boolean) {
        binding.progressBar.visibility = View.VISIBLE
        binding.placeHolder.visibility = View.GONE
        binding.recycleList.visibility = View.GONE
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            val filteredTransactionsList = allTransactionsList.filter { transaction ->
                transaction.transactionName.toLowerCase().contains(searchText) &&
                        (showCardTransactions && transaction.isCreditCard || !showCardTransactions && !transaction.isCreditCard)
            }

            binding.progressBar.visibility = View.GONE
            binding.recycleList.visibility = View.VISIBLE
            if (filteredTransactionsList.isEmpty()) {
                binding.placeHolder.visibility = View.VISIBLE
                binding.recycleList.visibility = View.GONE
                binding.placeHolderText.text = "Нет результатов поиска"
            } else {
                if(binding.progressBar.visibility == View.VISIBLE)
                    binding.placeHolder.visibility = View.GONE
                binding.placeHolder.visibility = View.GONE
                binding.recycleList.visibility = View.VISIBLE
                (binding.recycleList.adapter as? CardTransactionAdapter)?.filterList(filteredTransactionsList)
                (binding.recycleList.adapter as? CashTransactionAdapter)?.filterList(filteredTransactionsList)
            }
        }, 1000L)
    }

    private fun onSearchQueryChanged(query: String, showAllTransactionsFlag: Boolean) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            performSearch(query, showAllTransactionsFlag)
        }, 2000)
    }

    private fun performSearch(query: String, showAllTransactionsFlag: Boolean) {
        filterTransactions(query, showAllTransactionsFlag)
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