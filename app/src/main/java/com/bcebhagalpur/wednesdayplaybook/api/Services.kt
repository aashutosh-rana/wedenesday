package com.bcebhagalpur.wednesdayplaybook.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Services {

    fun getInstance(): ApiSongService? {
        val baseUrl = "https://itunes.apple.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create<ApiSongService>(ApiSongService::class.java)
    }
}