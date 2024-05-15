package com.example.financetracker.data

data class Transaction(
    val isIncome: Boolean,
    val isCreditCard: Boolean,
    val transactionName: String,
    val category: String,
    val amount: Int,
    val date: String,
    val cardNumber: Long,
    val cashName: String
)
