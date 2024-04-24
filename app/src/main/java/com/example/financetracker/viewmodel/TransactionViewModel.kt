package com.example.financetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.data.Transaction

class TransactionViewModel : ViewModel() {
    public val transactionsLiveData = MutableLiveData<List<Transaction>>()

    var transactionsList: List<Transaction>
        get() = transactionsLiveData.value ?: emptyList()
        set(value) {
            transactionsLiveData.value = value
        }

    fun addTransaction(transaction: Transaction) {
        val currentList = transactionsLiveData.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        transactionsLiveData.postValue(currentList)
    }
}