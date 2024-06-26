package com.example.financetracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.R
import com.example.financetracker.api.ApiService
import com.example.financetracker.data.Card
import com.example.financetracker.data.Cash
import com.example.financetracker.data.Transaction
import com.example.financetracker.network.RetrofitClient
import com.github.mikephil.charting.data.PieEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionViewModel : ViewModel() {
    private val transactionsLiveData = MutableLiveData<List<Transaction>>()
    private var selectedTransaction: Transaction? = null

    var transactionsList: List<Transaction>
        get() = transactionsLiveData.value ?: emptyList()
        set(value) {
            transactionsLiveData.value = value
        }

    fun addCardTransaction(transaction: Transaction, selectedCardNumber: Long, cardViewModel: CardViewModel) {
        val currentList = transactionsLiveData.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        transactionsLiveData.postValue(currentList)

        val selectedCard = cardViewModel.cardsList.find { it.cardNumber == selectedCardNumber }
        selectedCard?.let {
            if (transaction.isIncome) {
                it.cardBalance += transaction.amount
            } else {
                it.cardBalance -= transaction.amount
            }
            cardViewModel.getCardsLiveData().postValue(cardViewModel.cardsList)
        }
    }

    fun addCashTransaction(transaction: Transaction, selectedCashName: String, cashViewModel: CashViewModel){
        val currentList = transactionsLiveData.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        transactionsLiveData.postValue(currentList)

        val selectedCash = cashViewModel.cashList.find { it.cashName == selectedCashName }
        selectedCash?.let {
            if(transaction.isIncome){
                it.cashBalance += transaction.amount
            } else {
                it.cashBalance -= transaction.amount
            }
            cashViewModel.getCashesLiveData().postValue(cashViewModel.cashList)
        }
    }

    fun getTransactionsForSelectedCard(selectedCard: Card): List<Transaction> {
        return transactionsList.filter { it.cardNumber == selectedCard.cardNumber }
    }

    fun getTransactionsForSelectedCash(selectedCash: Cash): List<Transaction> {
        return transactionsList.filter { it.cashName == selectedCash.cashName }
    }

    fun getTransactionsByEmail(email: String?) {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getTransactionsByEmail(email)

        call.enqueue(object : Callback<List<Transaction>> {
            override fun onResponse(call: Call<List<Transaction>>, response: Response<List<Transaction>>) {
                response.body()?.let {
                    transactionsList = it
                    transactionsLiveData.postValue(it)
                    Log.d("DEBUG", transactionsLiveData.toString())
                }
            }

            override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
                Log.e("TransactionViewModel", "Error getting transactions", t)
            }
        })
    }

    fun deleteTransaction(email: String?, transactionName: String) {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.deleteTransaction(email, transactionName)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val updatedList = transactionsList.toMutableList()
                    updatedList.removeAll { it.transactionName == transactionName }
                    transactionsLiveData.postValue(updatedList)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("TransactionViewModel", "Error deleting transaction", t)
                val updatedList = transactionsList.toMutableList()
                updatedList.removeAll { it.transactionName == transactionName }
                transactionsLiveData.postValue(updatedList)
            }
        })
    }

    fun setSelectedTransaction(transaction: Transaction){
        selectedTransaction = transaction
    }

    fun getSelectedTransaction() : Transaction?{
        return selectedTransaction
    }

    fun getTransactionEntriesForPieChart(): List<PieEntry> {
        val groupedTransactions = transactionsList.groupBy { it.category }
        val entries = mutableListOf<PieEntry>()
        groupedTransactions.forEach { (category, transactions) ->
            val totalAmount = transactions.sumBy { it.amount }
            val categoryName = when (category) {
                "imageHealth" -> "Здоровье"
                "imageFood" -> "Еда"
                "imageInvestment" -> "Инвестиции"
                "imageCar" -> "Машина"
                "imageGift" -> "Подарок"
                "imageClothes" -> "Одежда"
                "imageDonation" -> "Благотворительность"
                "imageBeauty" -> "Красота"
                "imageHome" -> "Дом"
                "imageBusiness" -> "Бизнесс"
                "imageMoney" -> "Зарплата"
                else -> "???"
            }
            entries.add(PieEntry(totalAmount.toFloat(), categoryName))
        }

        return entries
    }

    fun getTransactionLiveData(): MutableLiveData<List<Transaction>> {
        return transactionsLiveData
    }

    fun deleteTransactions(){
        transactionsLiveData.value = emptyList<Transaction>()
    }
}