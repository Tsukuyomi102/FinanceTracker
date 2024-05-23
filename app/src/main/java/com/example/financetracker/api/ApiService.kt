package com.example.financetracker.api

import com.example.financetracker.data.Card
import com.example.financetracker.data.Cash
import com.example.financetracker.data.Transaction
import com.example.financetracker.data.User
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //Users requests
    @FormUrlEncoded
    @POST("user")
    fun addUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Integer>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<User>

    //Card requests
    @FormUrlEncoded
    @POST("user/card")
    fun addCard(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("balance") balance: Int,
        @Field("number") number: Long,
        @Field("month") month: Int,
        @Field("year") year: Int
    ): Call<Integer>

    @GET("user/{email}/card")
    fun getCardsByEmail(
        @Path("email") email: String?
    ): Call<List<Card>>

    //Cash requests
    @FormUrlEncoded
    @POST("user/cash")
    fun addCash(
        @Field("email") email: String,
        @Field("balance") balance: Int,
        @Field("name") name: String,
        @Field("description") description: String
    ): Call<Integer>

    @GET("user/{email}/cash")
    fun getCashByEmail(
        @Path("email") email: String?
    ): Call<List<Cash>>

    //Transaction requests
    @FormUrlEncoded
    @POST("user/transaction")
    fun addTransaction(
        @Field("email") email: String,
        @Field("isIncome") isIncome: Boolean,
        @Field("isCreditCard") isCreditCard: Boolean,
        @Field("name") name: String,
        @Field("category") category: String,
        @Field("amount") amount: Int,
        @Field("date") date: String,
        @Field("cardNumber") cardNumber: Long,
        @Field("cashName") cashName: String
    ): Call<Integer>

    @GET("user/{email}/transactions")
    fun getTransactionsByEmail(
        @Path("email") email: String?
    ): Call<List<Transaction>>
    @DELETE("user/{email}/transaction/{name}")
    fun deleteTransaction(
        @Path("email") email: String,
        @Path("name") name: String
    ): Call<String>

}