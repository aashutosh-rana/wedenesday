package com.bcebhagalpur.wednesdayplaybook.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bcebhagalpur.wednesdayplaybook.R
import com.bcebhagalpur.wednesdayplaybook.adapter.WordListAdapter
import com.bcebhagalpur.wednesdayplaybook.model.TrackSong
import com.bcebhagalpur.wednesdayplaybook.model.Word
import com.bcebhagalpur.wednesdayplaybook.room.WordRoomDatabase

class HistoryActivity : AppCompatActivity() {

    lateinit var recyclerFavourite: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: WordListAdapter
    var wordList = listOf<Word>()
//    private val dataTracks: MutableList<TrackSong> = ArrayList()
//    private lateinit var  btnDelete:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

//        btnDelete=findViewById(R.id.btnDelete)
        recyclerFavourite =findViewById(R.id.recyclerView)
        layoutManager = GridLayoutManager(this, 2)
        wordList = RetrieveWords(this).execute().get()

            recyclerAdapter = WordListAdapter(wordList,this)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
//        btnDelete.setOnClickListener {
////            wordList=deleteWords(this).execute().get()
//        }
    }

    class RetrieveWords(val context: Context) : AsyncTask<Void, Void, List<Word>>() {
        override fun doInBackground(vararg p0: Void?): List<Word> {
            val db = Room.databaseBuilder(context, WordRoomDatabase::class.java, "word-table").fallbackToDestructiveMigration().build()
            return db.wordDao().getAlphabetizedWords()
        }

    }

//    class deleteWords(val context: Context) : AsyncTask<Void, Void, List<Word>>() {
//        override fun doInBackground(vararg p0: Void?): List<Word> {
//            val db = Room.databaseBuilder(context, WordRoomDatabase::class.java, "word-table").build()
//            return db.wordDao().deleteAll()
//        }

//    }

    }
