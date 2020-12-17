package com.bcebhagalpur.wednesdayplaybook.activity

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.*
import com.bcebhagalpur.wednesdayplaybook.R
import com.bcebhagalpur.wednesdayplaybook.adapter.SongAdapter
import com.bcebhagalpur.wednesdayplaybook.model.TrackSong
import com.bcebhagalpur.wednesdayplaybook.utils.ConnectionManager
import com.bcebhagalpur.wednesdayplaybook.view.SongListContract
import com.bcebhagalpur.wednesdayplaybook.view.SongListPresenter

class MainActivity : AppCompatActivity(), SongListContract.View {

    var context: Context? = null
    var main: LinearLayout? = null
    var txtNoSongs: TextView? = null
    var listTracks: RecyclerView? = null
    private lateinit var progressBar: ProgressBar

    private val dataTracks: MutableList<TrackSong> = ArrayList()

    private var adapter: SongAdapter? = null
    var presenter: SongListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        context = this@MainActivity
        main = findViewById<View>(R.id.song_list_main) as LinearLayout
        txtNoSongs = findViewById<View>(R.id.txtNoSongs) as TextView
        listTracks = findViewById<View>(R.id.listSongs) as RecyclerView
        if (ConnectionManager().checkConnectivity(this)) {
            adapter = SongAdapter(context as MainActivity, dataTracks, this)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 2)
        listTracks!!.layoutManager = mLayoutManager
        listTracks!!.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        listTracks!!.itemAnimator = DefaultItemAnimator()
        listTracks!!.adapter = adapter
    }else{
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Error")
            dialog.setMessage("Internet connection is not found")
            dialog.setPositiveButton("Show history"){text,listener ->
                val intent=Intent(this,HistoryActivity::class.java)
                startActivity(intent)
                finish()
            }
            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity(this)
            }
            dialog.create()
            dialog.show()
        }
}
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchView =
            MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search for Songs, Artists & More"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    fun search(strTerm: String?) {
        txtNoSongs!!.visibility = View.GONE
        listTracks!!.visibility = View.VISIBLE
        dataTracks.clear()
        adapter!!.notifyDataSetChanged()
        setLoadingIndicator(true)
        listTracks!!.postDelayed({ presenter.getTracks(strTerm) }, 2000)
    }

    override fun displayMessage(message: String?) {
        setLoadingIndicator(false)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun setLoadingIndicator(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility=View.VISIBLE
        } else {
            progressBar.visibility=View.GONE
        }
    }

    override fun displayTracks(dataTracks: List<TrackSong?>?) {
        setLoadingIndicator(false)
        this.dataTracks.clear()
            this.dataTracks.addAll(dataTracks as Collection<TrackSong>)
        adapter!!.notifyDataSetChanged()
    }

    init {
        presenter = SongListPresenter(this)
    }
}
