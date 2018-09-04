package io.jmdg.themoviedb.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.jmdg.themoviedb.R
import io.jmdg.themoviedb.adapters.MovieAdapter
import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.data.models.Request
import io.jmdg.themoviedb.providers.SearchContentProvider
import io.jmdg.themoviedb.utils.Utilities
import io.jmdg.themoviedb.utils.ViewModelFactory
import io.jmdg.themoviedb.utils.extensions.*
import io.jmdg.themoviedb.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.search_activity.*
import javax.inject.Inject


/**
 * Created by Joshua de Guzman on 05/09/2018.
 */

class SearchableActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mMovieAdapter: MovieAdapter
    private var page = 1
    private var isLoading = false
    private var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        handleIntent(intent)
        setUpViewModel()
        setUpComponents()
        subscribeMovieList()
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun setUpViewModel() {
        getAppInjector().inject(this)
        mMovieViewModel = getViewModel(viewModelFactory)
    }

    private fun setUpComponents() {
        // Setup RecyclerView
        mMovieAdapter = MovieAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvMovies.layoutManager = linearLayoutManager
        rvMovies.adapter = mMovieAdapter

        // Setup pagination
        // More on: https://android.jlelse.eu/android-recyclerview-pagination-e598dc0148f
        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
                    mMovieViewModel.getMovies(query, page++)
                }
            }
        })

        // Setup SwipeRefreshLayout
        srlMain.setOnRefreshListener {
            mMovieAdapter.clear()
            mMovieViewModel.getMovies(query, page)
        }
    }

    private fun subscribeMovieList() {
        mMovieViewModel.apply {
            observe(getMovies(query, page), ::onMovieLiveDataChanged)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            // Retrieve query
            val query = intent.getStringExtra(SearchManager.QUERY)
            this.query = query

            // Change toolbar title
            if (supportActionBar != null) {
                supportActionBar!!.title = query
            }

            // Save input query terms
            // More on: https://developer.android.com/guide/topics/search/adding-recent-query-suggestions
            val suggestions = SearchRecentSuggestions(this,
                    SearchContentProvider.AUTHORITY, SearchContentProvider.MODE)
            suggestions.saveRecentQuery(query, null)
        }
    }

    private fun onMovieLiveDataChanged(data: Data<Request<Movie>>?) {
        data.let { movies ->
            when (movies!!.dataState) {
                DataState.LOADING -> {
                    isLoading = true
                    Utilities.showMessage(findViewById(android.R.id.content), "Loading movies...")
                }

                DataState.SUCCESS -> {
                    resetLoadingIndicators()
                    mMovieAdapter.addMovies(movies.data!!.results)
                }

                DataState.ERROR -> {
                    resetLoadingIndicators()
                    Utilities.showMessage(findViewById(android.R.id.content), "Error loading data, please try again later.", Snackbar.LENGTH_INDEFINITE)
                }
            }
        }
    }

    private fun resetLoadingIndicators() {
        isLoading = false
        srlMain.isRefreshing = false
    }

}