package com.bcebhagalpur.wednesdayplaybook.view

import com.bcebhagalpur.wednesdayplaybook.model.Track

class SongDetailContract {
    internal interface View {
        fun displayMessage(message: String?)
        fun displayTrack(track: Track?)
    }
}