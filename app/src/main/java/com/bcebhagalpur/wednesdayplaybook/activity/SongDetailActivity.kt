package com.bcebhagalpur.wednesdayplaybook.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bcebhagalpur.wednesdayplaybook.R
import com.bcebhagalpur.wednesdayplaybook.model.TrackSong
import com.bumptech.glide.Glide

class SongDetailActivity : AppCompatActivity() {

    var context: Context? = null
    var main: LinearLayout? = null
    var imgArtwork: ImageView? = null
    var txtArtistName: TextView? = null
    var txtGenre:TextView?=null
    var txtPrice:TextView?=null
    var videoView: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

          context = this@SongDetailActivity

        main = findViewById(R.id.song_detail_main)
        imgArtwork = findViewById(R.id.imgArtworkDetail)
        txtArtistName = findViewById(R.id.artist_name_detail)
        txtGenre = findViewById(R.id.genre_detail)
        txtPrice =  findViewById(R.id.price_detail)
        videoView = findViewById(R.id.videoView)
        try {
            displayTrack(intent.getSerializableExtra("track") as TrackSong)
        } catch (e: Exception) {
            displayMessage("Problem while getting song info, Try again.")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun displayMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    fun displayTrack(track: TrackSong) {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setTitle(track.getTrackName())
        }
        val artworkUrl: String? = track.getArtworkUrl100()
        Glide.with(context!!).load(artworkUrl).placeholder(R.drawable.ic_logo).into(imgArtwork!!)
        txtArtistName?.setText(track.getArtistName())
        txtGenre?.setText(track.getPrimaryGenreName())
        txtPrice!!.text = String.format("US $ %s", java.lang.String.valueOf(track.getTrackPrice()))
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        val video: Uri = Uri.parse(track.getPreviewUrl())
        videoView!!.setMediaController(mediaController)
        videoView!!.setVideoURI(video)
        videoView!!.start()
    }
}
