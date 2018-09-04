package io.jmdg.themoviedb.ui.main

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import io.jmdg.themoviedb.R
import io.jmdg.themoviedb.activities.SearchableActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu!!.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(this, SearchableActivity::class.java)))
        searchView.queryHint = getString(R.string.search_hint)
        searchView.isIconified = false
        searchView.setIconifiedByDefault(false)
        searchView.setOnCloseListener {
            true
        }
        searchManager.setOnCancelListener {
            searchView.queryHint = getString(R.string.search_hint)
        }
        return true
    }

}
