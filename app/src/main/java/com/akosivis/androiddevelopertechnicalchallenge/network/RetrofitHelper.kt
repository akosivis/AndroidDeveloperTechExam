package com.akosivis.androiddevelopertechnicalchallenge.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit as Retrofit2

object RetrofitHelper {
    private const val BASE_URL = "https://dummyjson.com/"
    fun getInstance(): Retrofit2 {
        return Retrofit2.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}