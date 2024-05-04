    package com.example.financetracker.viewmodel

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import com.example.financetracker.data.Card
    import com.example.financetracker.data.Cash
    import com.example.financetracker.data.Transaction

    class TransactionViewModel : ViewModel() {
        private val transactionsLiveData = MutableLiveData<List<Transaction>>()

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
    }