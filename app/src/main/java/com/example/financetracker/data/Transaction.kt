package com.example.financetracker.data

import java.util.Date

class Transaction {

    private var type: Boolean
    private var paymentMethod: Boolean
    private var date: Date
    private var transactionName: String
    private var category: String
    private var amount: Int

    constructor(_type: Boolean, _paymentMethod: Boolean, _date: Date,
                _transactionName: String, _category: String, _amount: Int) {
        type = _type
        paymentMethod = _paymentMethod
        date = _date
        transactionName = _transactionName
        category = _category
        amount = _amount
    }

    public fun getType() : Boolean {
        return this.type
    }
}