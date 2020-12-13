package com.bcebhagalpur.wednesdayplaybook.view

import com.bcebhagalpur.wednesdayplaybook.model.TrackSong

class SongListContract {
    interface View {
        fun displayMessage(message: String?)
        fun setLoadingIndicator(isLoading: Boolean)
        fun displayTracks(dataTracks: List<TrackSong?>?)
    }

    internal interface Presenter {
        fun getTracks(term: String?)
    }
}