package com.example.financetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.data.Transaction

class TransactionViewModel : ViewModel() {
    private val transactionsLiveData = MutableLiveData<List<Transaction>>()

    fun getTransactionLiveData(): LiveData<List<Transaction>> {
        return transactionsLiveData
    }

    fun addTransaction(transaction: Transaction) {
        val currentList = transactionsLiveData.value.orEmpty().toMutableList()
        currentList.add(transaction)
        transactionsLiveData.value = currentList
    }
}