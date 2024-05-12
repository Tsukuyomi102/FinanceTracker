package com.example.financetracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.api.ApiService
import com.example.financetracker.data.Cash
import com.example.financetracker.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun addCashByEmail(email: String, cash: Cash){
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.addCash(
            email,
            cash.cashBalance,
            cash.cashName,
            cash.cashDescription
        )

        call.enqueue(object : Callback<Integer> {
            override fun onResponse(call: Call<Integer>, response: Response<Integer>) {
                if (response.isSuccessful) {
                    println("Cash added successfully")
                    addCash(cash)
                } else {
                    println("Failed to add cash!")
                }
            }

            override fun onFailure(call: Call<Integer>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    fun getCashByEmail(email: String) {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getCashByEmail(email)
        call.enqueue(object : Callback<List<Cash>> {
            override fun onResponse(call: Call<List<Cash>>, response: Response<List<Cash>>) {
                if (response.isSuccessful) {
                    val cashes = response.body()
                    cashes?.let {
                        cashList = it
                    }
                    Log.d("LOGGED", cashList.toString())
                } else {
                    println("Failed to get cash!")
                }
            }

            override fun onFailure(call: Call<List<Cash>>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
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