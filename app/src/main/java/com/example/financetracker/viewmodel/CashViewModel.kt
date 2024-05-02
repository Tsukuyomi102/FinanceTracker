package com.example.financetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.data.Card
import com.example.financetracker.data.Cash

class CashViewModel : ViewModel() {
    private val cashLiveData = MutableLiveData<List<Cash>>()

    var cashList: List<Cash>
        get() = cashLiveData.value ?: emptyList()
        set(value) {
            cashLiveData.value = value
        }

    fun addCash(cash: Cash) {
        val currentList = cashLiveData.value?.toMutableList() ?: mutableListOf()
        currentList.add(cash)
        cashLiveData.postValue(currentList)
    }
}