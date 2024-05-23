package com.example.financetracker.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://158.160.91.176:8080/")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
}