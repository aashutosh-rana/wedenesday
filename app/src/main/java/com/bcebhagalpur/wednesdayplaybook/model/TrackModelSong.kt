package com.bcebhagalpur.wednesdayplaybook.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrackModelSong {
    @SerializedName("resultCount")
    @Expose
    private var resultCount: Int? = null

    @SerializedName("results")
    @Expose
    private var tracks: List<TrackSong?>? = null

    fun getResultCount(): Int? {
        return resultCount
    }

    fun setResultCount(resultCount: Int?) {
        this.resultCount = resultCount
    }

    fun getTracks(): List<TrackSong?>? {
        return tracks
    }

    fun setTracks(tracks: List<TrackSong?>?) {
        this.tracks = tracks
    }
}