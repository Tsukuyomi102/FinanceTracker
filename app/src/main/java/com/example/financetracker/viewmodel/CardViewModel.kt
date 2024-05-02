package com.example.financetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.data.Card

class CardViewModel : ViewModel() {
    private val cardsLiveData = MutableLiveData<List<Card>>()

    var cardsList: List<Card>
        get() = cardsLiveData.value ?: emptyList()
        set(value) {
            cardsLiveData.value = value
        }

    fun addCard(card: Card) {
        val currentList = cardsLiveData.value?.toMutableList() ?: mutableListOf()
        currentList.add(card)
        cardsLiveData.postValue(currentList)
    }
}