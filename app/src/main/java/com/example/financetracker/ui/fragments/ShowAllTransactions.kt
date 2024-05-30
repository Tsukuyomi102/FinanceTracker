package com.example.financetracker.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
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
import com.example.financetracker.ui.adapters.SearchHistoryAdapter
import com.example.financetracker.viewmodel.TransactionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowAllTransactions : Fragment(), CardTransactionAdapter.OnCardTransactionClickListener, CashTransactionAdapter.OnCashTransactionClickListener {
    private lateinit var binding: FragmentShowAllTransactionsBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var allTransactionsList: List<Transaction>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var handler: Handler
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowAllTransactionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        allTransactionsList = viewModel.transactionsList.toList()
        handler = Handler(Looper.getMainLooper())
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())


        val searchHistoryRecyclerView = binding.searchHistoryRecyclerView
        searchHistoryRecyclerView.layoutManager = LinearLayoutManager(context)

        searchHistoryRecyclerView.visibility = View.GONE

        val searchHistory = loadSearchHistory()
        searchHistoryAdapter = SearchHistoryAdapter(searchHistory)
        searchHistoryRecyclerView.adapter = searchHistoryAdapter

        val showAllTransactionsFlag = arguments?.getBoolean("showAllTransactions") ?: false
        if (showAllTransactionsFlag) {
            showCardTransactions()
        } else {
            showCashTransactions()
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
        var isShowingHistory = true

        binding.imageSearch.setOnClickListener {
            if (isShowingHistory) {
                searchHistoryRecyclerView.visibility = View.GONE
                binding.recycleList.visibility = View.VISIBLE
                isShowingHistory = false
            } else {
                searchHistoryRecyclerView.visibility = View.VISIBLE
                binding.recycleList.visibility = View.GONE
                isShowingHistory = true
            }
        }
        return binding.root
    }

    private fun showCardTransactions() {
        binding.toolBarName.text = "Карточные операции"
        binding.recycleList.layoutManager = LinearLayoutManager(context)
        val filteredCardTransactionsList = allTransactionsList.filter { it.isCreditCard }
        val cardTransactionAdapter = CardTransactionAdapter(filteredCardTransactionsList, requireContext(), true, this)
        binding.recycleList.adapter = cardTransactionAdapter
    }

    private fun showCashTransactions() {
        binding.toolBarName.text = "Наличные операции"
        binding.recycleList.layoutManager = LinearLayoutManager(context)
        val filteredCashTransactionsList = allTransactionsList.filter { !it.isCreditCard }
        val cashTransactionAdapter = CashTransactionAdapter(filteredCashTransactionsList, requireContext(), true, this)
        binding.recycleList.adapter = cashTransactionAdapter
    }

    private fun filterTransactions(searchText: String, showCardTransactions: Boolean) {
        binding.progressBar.visibility = View.VISIBLE
        binding.recycleList.visibility = View.GONE
        Handler(Looper.getMainLooper()).postDelayed({
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
                binding.placeHolder.visibility = View.GONE
                binding.recycleList.visibility = View.VISIBLE
                if (showCardTransactions) {
                    (binding.recycleList.adapter as? CardTransactionAdapter)?.filterList(filteredTransactionsList)
                } else {
                    (binding.recycleList.adapter as? CashTransactionAdapter)?.filterList(filteredTransactionsList)
                }
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
        saveSearchHistory(query)
        updateSearchHistory()
    }

    private fun saveSearchHistory(query: String) {
        val editor = sharedPreferences.edit()
        val history = sharedPreferences.getStringSet("searchHistory", HashSet<String>()) ?: HashSet()
        history.add(query)
        editor.putStringSet("searchHistory", history)
        editor.apply()
    }

    private fun loadSearchHistory(): List<String> {
        return sharedPreferences.getStringSet("searchHistory", setOf())?.toList() ?: listOf()
    }

    private fun updateSearchHistory() {
        val searchHistory = loadSearchHistory()
        searchHistoryAdapter.updateList(searchHistory)
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