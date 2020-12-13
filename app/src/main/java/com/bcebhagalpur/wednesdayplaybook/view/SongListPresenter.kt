package com.bcebhagalpur.wednesdayplaybook.view

import com.bcebhagalpur.wednesdayplaybook.api.ApiService
import com.bcebhagalpur.wednesdayplaybook.api.ServiceFactory
import com.bcebhagalpur.wednesdayplaybook.model.TrackModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongListPresenter(songListView: SongListContract.View) : SongListContract.Presenter {
    private val songListView: SongListContract.View = songListView

    override fun getTracks(term: String?) {
        val service: ApiService = ServiceFactory().getInstance()!!
        val trackModelCall: Call<TrackModel?>? = service.getTracks(term)
        trackModelCall!!.enqueue(object : Callback<TrackModel?> {
            override fun onResponse(call: Call<TrackModel?>, response: Response<TrackModel?>) {
                val trackModel: TrackModel? = response.body()
                if (trackModel!!.getResultCount()!! > 0) {
                        songListView.displayTracks(trackModel.getTracks())
                } else {
                    songListView.displayMessage("No songs found, Try again.")
                }
            }

            override fun onFailure(call: Call<TrackModel?>, t: Throwable) {
                songListView.displayMessage("No songs found, Try again.")
            }
        })
    }

}
