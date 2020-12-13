package com.bcebhagalpur.wednesdayplaybook.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.*
import com.bcebhagalpur.wednesdayplaybook.R
import com.bcebhagalpur.wednesdayplaybook.adapter.SongAdapter
import com.bcebhagalpur.wednesdayplaybook.model.Track
import com.bcebhagalpur.wednesdayplaybook.view.SongListContract
import com.bcebhagalpur.wednesdayplaybook.view.SongListPresenter
import com.cooltechworks.views.shimmer.ShimmerRecyclerView


class MainActivity : AppCompatActivity(), SongListContract.View {

    var context: Context? = null
    var main: LinearLayout? = null
    var txtNoSongs: TextView? = null
    var listTracks: RecyclerView? = null
    private lateinit var progressBar: ProgressBar

    private val dataTracks: MutableList<Track> = ArrayList()

    private var adapter: SongAdapter? = null
    var presenter: SongListPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar=findViewById(R.id.progressBar)
        context = this@MainActivity
        main = findViewById<View>(R.id.song_list_main) as LinearLayout
        txtNoSongs = findViewById<View>(R.id.txtNoSongs) as TextView
        listTracks = findViewById<View>(R.id.listSongs) as RecyclerView
        adapter = SongAdapter(context as MainActivity, dataTracks)
        val mLayoutManager: RecyclerView.LayoutManager =GridLayoutManager(applicationContext,2)
        listTracks!!.layoutManager = mLayoutManager
        listTracks!!.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        listTracks!!.itemAnimator = DefaultItemAnimator()
        listTracks!!.adapter = adapter
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

    override fun displayTracks(dataTracks: List<Track?>?) {
        setLoadingIndicator(false)
        this.dataTracks.clear()
            this.dataTracks.addAll(dataTracks as Collection<Track>)
        adapter!!.notifyDataSetChanged()
    }

    init {
        presenter = SongListPresenter(this)
    }
}
