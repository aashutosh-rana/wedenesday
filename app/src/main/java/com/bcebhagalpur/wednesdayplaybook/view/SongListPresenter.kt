package com.bcebhagalpur.wednesdayplaybook.view

import com.bcebhagalpur.wednesdayplaybook.api.ApiSongService
import com.bcebhagalpur.wednesdayplaybook.api.Services
import com.bcebhagalpur.wednesdayplaybook.model.TrackModelSong
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongListPresenter(songListView: SongListContract.View) : SongListContract.Presenter {
    private val songListView: SongListContract.View = songListView

    override fun getTracks(term: String?) {
        val service: ApiSongService = Services().getInstance()!!
        val trackModelCall: Call<TrackModelSong?>? = service.getTracks(term)
        trackModelCall!!.enqueue(object : Callback<TrackModelSong?> {
            override fun onResponse(call: Call<TrackModelSong?>, response: Response<TrackModelSong?>) {
                val trackModel: TrackModelSong? = response.body()
                if (trackModel!!.getResultCount()!! > 0) {
                        songListView.displayTracks(trackModel.getTracks())
                } else {
                    songListView.displayMessage("No songs found, Try again.")
                }
            }

            override fun onFailure(call: Call<TrackModelSong?>, t: Throwable) {
                songListView.displayMessage("No songs found, Try again.")
            }
        })
    }

}
