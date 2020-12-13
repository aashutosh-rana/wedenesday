package com.bcebhagalpur.wednesdayplaybook.api

import com.bcebhagalpur.wednesdayplaybook.model.TrackModelSong
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Call;



interface ApiSongService {
    @GET("search")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getTracks(@Query("term") term: String?): Call<TrackModelSong?>?
}