package com.bcebhagalpur.wednesdayplaybook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bcebhagalpur.wednesdayplaybook.R
import com.bcebhagalpur.wednesdayplaybook.model.TrackSong
import com.bcebhagalpur.wednesdayplaybook.model.Word
import com.bcebhagalpur.wednesdayplaybook.room.WordRoomDatabase

class WordListAdapter( val wordList: List<Word>,val context: Context ) :
    RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgTrackArtwork: ImageView = view.findViewById(R.id.artwork)
        val txtTrackName: TextView = view.findViewById(R.id.trackName)
        val txtArtistNameNGenre: TextView = view.findViewById(R.id.artistName)
        val txtPrice: TextView = view.findViewById(R.id.price)
        val imgCut: ImageView = view.findViewById(R.id.imgCut)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.local_database_adapter, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
//        val track = trackList[position]
        val word = wordList[position]
        holder.txtTrackName.text = word.trackName
        holder.txtArtistNameNGenre.text = word.artistName
        holder.txtPrice.text = word.primaryGenreName
        holder.imgTrackArtwork.setImageResource(R.drawable.ic_logo)
        holder.imgCut.setOnClickListener {
//            val word1 = Word(
//                track.getArtworkUrl100().toString(),track.getTrackName().toString(),
//                track.getArtistName().toString(),track.getTrackPrice().toString()
//            )
            val db = Room.databaseBuilder(context, WordRoomDatabase::class.java, "word-table").allowMainThreadQueries().build()
            db.wordDao().delete(word)
        }

    }

    override fun getItemCount(): Int {
        return wordList.size
    }

}