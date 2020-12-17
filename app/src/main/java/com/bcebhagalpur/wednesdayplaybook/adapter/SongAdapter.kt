package com.bcebhagalpur.wednesdayplaybook.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.room.Room
import com.bcebhagalpur.wednesdayplaybook.R
import com.bcebhagalpur.wednesdayplaybook.activity.SongDetailActivity
import com.bcebhagalpur.wednesdayplaybook.model.TrackSong
import com.bcebhagalpur.wednesdayplaybook.model.Word
import com.bcebhagalpur.wednesdayplaybook.room.WordRoomDatabase
import com.bumptech.glide.Glide

class SongAdapter (var context: Context, var trackList: List<TrackSong> , var viewModelStoreOwner: ViewModelStoreOwner) : RecyclerView.Adapter<SongAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : ViewHolder(view) {
        var row: LinearLayout
        var imgTrackArtwork: ImageView
        var txtTrackName: TextView
        var txtArtistNameNGenre: TextView
        var txtPrice: TextView

        init {
            row = view.findViewById<View>(R.id.song_item_row) as LinearLayout
            imgTrackArtwork = view.findViewById<View>(R.id.artwork) as ImageView
            txtTrackName = view.findViewById<View>(R.id.track_name) as TextView
            txtArtistNameNGenre = view.findViewById<View>(R.id.artist_name_and_genre) as TextView
            txtPrice = view.findViewById<View>(R.id.price) as TextView

        }
    }

    fun SongAdapter(context: Context?, trackList: List<TrackSong?>?) {
        this.context = context!!
        this.trackList = trackList as List<TrackSong>
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val track = trackList[position]
        val artworkUrl = track.getArtworkUrl100()
        Glide.with(context).load(artworkUrl).placeholder(R.drawable.ic_logo)
            .into(holder.imgTrackArtwork)
        holder.txtTrackName.setText(track.getTrackName())
        holder.txtArtistNameNGenre.setText(
            track.getArtistName().toString() + " | " + track.getPrimaryGenreName()
        )
        holder.txtPrice.setText(
            String.format(
                "US $ %s",
                java.lang.String.valueOf(track.getTrackPrice())
            )
        )
        holder.row.setOnClickListener {
            val word = Word(
                track.getCollectionId()!!.toInt(),
               track.getArtworkUrl100().toString(),track.getTrackName().toString(),
                track.getArtistName().toString(),track.getTrackPrice().toString()
            )
            val db = Room.databaseBuilder(context, WordRoomDatabase::class.java, "word-table").fallbackToDestructiveMigration().allowMainThreadQueries().build()

            val detail = Intent(context, SongDetailActivity::class.java)
            detail.putExtra("track",trackList.get(position))
            context.startActivity(detail)
try {
    db.wordDao().insert(word)
}catch (e:Exception){
    e.printStackTrace()
}

        }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

}
