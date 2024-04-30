package com.example.financetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.data.Aim

class AimViewModel : ViewModel() {
    private val aimLiveData = MutableLiveData<List<Aim>>()

    var aimList: List<Aim>
        get() = aimLiveData.value ?: emptyList()
        set(value) {
            aimLiveData.value = value
        }

    fun addAim(aim: Aim){
        val currentList = aimLiveData.value?.toMutableList() ?: mutableListOf()
        currentList.add(aim)
        aimLiveData.postValue(currentList)
    }
}