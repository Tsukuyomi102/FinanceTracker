package com.example.financetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.data.Cash

class CashViewModel : ViewModel() {
    private val cashLiveData = MutableLiveData<List<Cash>>()
    private var selectedCash: Cash? = null

    fun getCashesLiveData(): MutableLiveData<List<Cash>> {
        return cashLiveData
    }

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

    fun getTotalBalance(): Int {
        return cashList.sumOf { it.cashBalance }
    }

    fun setSelectedCash(cash: Cash){
        selectedCash = cash
    }

    fun getSelectedCash() : Cash?{
        return selectedCash
    }
}