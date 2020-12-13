package com.bcebhagalpur.wednesdayplaybook.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceFactory {

    fun getInstance(): ApiService? {
        val baseUrl = "https://itunes.apple.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create<ApiService>(ApiService::class.java)
    }
}