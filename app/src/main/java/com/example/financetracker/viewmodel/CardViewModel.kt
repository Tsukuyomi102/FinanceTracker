package com.example.financetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.api.ApiService
import com.example.financetracker.data.Card
import com.example.financetracker.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardViewModel : ViewModel() {
    private val cardsLiveData = MutableLiveData<List<Card>>()
    private var selectedCard: Card? = null

    fun getCardsLiveData(): MutableLiveData<List<Card>> {
        return cardsLiveData
    }

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

    fun addCardByEmail(email: String, card: Card) {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.addCard(
            email,
            card.cardName,
            card.cardBalance,
            card.cardNumber,
            card.month,
            card.year)
        call.enqueue(object : Callback<Integer> {
            override fun onResponse(call: Call<Integer>, response: Response<Integer>) {
                if(response.isSuccessful){
                    println("Card added successfully")
                    addCard(card)
                } else {
                    println("Failed to add card!")
                }
            }

            override fun onFailure(call: Call<Integer>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    fun getCardsByEmail(email: String) {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getCardsByEmail(email)
        call.enqueue(object : Callback<List<Card>> {
            override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                if(response.isSuccessful){
                    cardsList = response.body() ?: emptyList()
                    println(response.body())
                } else {
                    println("Failed to get cards!")
                }
            }

            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    fun getTotalBalance(): Int {
        return cardsList.sumOf { it.cardBalance }
    }

    fun setSelectedCard(card: Card){
        selectedCard = card
    }

    fun getSelectedCard() : Card?{
        return selectedCard
    }
}