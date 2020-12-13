package com.bcebhagalpur.wednesdayplaybook.view

import com.bcebhagalpur.wednesdayplaybook.model.TrackSong

class SongDetailContract {
    internal interface View {
        fun displayMessage(message: String?)
        fun displayTrack(track: TrackSong?)
    }
}