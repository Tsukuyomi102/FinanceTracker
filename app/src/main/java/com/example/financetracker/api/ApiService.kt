package com.example.financetracker.api

import com.example.financetracker.data.Card
import com.example.financetracker.data.Cash
import com.example.financetracker.data.User
import retrofit2.Call
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
        @Path("email") email: String
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
        @Path("email") email: String
    ): Call<List<Cash>>
}