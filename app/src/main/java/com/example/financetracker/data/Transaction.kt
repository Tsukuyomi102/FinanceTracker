package com.example.financetracker.data

import java.util.Date

data class Transaction(
    val isIncome: Boolean,
    val isCreditCard: Boolean,
    val transactionName: String,
    val category: String,
    val amount: Int,
    val date: String
)
