package io.jmdg.themoviedb.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.jmdg.themoviedb.R
import io.jmdg.themoviedb.adapters.MovieAdapter
import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.data.models.Request
import io.jmdg.themoviedb.helpers.ItemClickListener
import io.jmdg.themoviedb.utils.Utilities
import io.jmdg.themoviedb.utils.ViewModelFactory
import io.jmdg.themoviedb.utils.extensions.*
import io.jmdg.themoviedb.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(), ItemClickListener<Movie> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mMovieAdapter: MovieAdapter
    private var page = 1
    private var isLoading = false

    companion object {
        var TAG = MainFragment::class.java.simpleName
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpComponents()
        subscribeMovieList()
    }

    private fun setUpViewModel() {
        getAppInjector().inject(this)
        mMovieViewModel = activity!!.getViewModel(viewModelFactory)
    }

    private fun setUpComponents() {
        // Setup RecyclerView
        mMovieAdapter = MovieAdapter(context!!)
        mMovieAdapter.itemClickListener = this
        val gridLayoutManager = GridLayoutManager(context, 2)
        rvMovies.layoutManager = gridLayoutManager
        rvMovies.adapter = mMovieAdapter

        // Setup pagination
        // More on: https://android.jlelse.eu/android-recyclerview-pagination-e598dc0148f
        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
                    mMovieViewModel.getPopular(page++)
                }
            }
        })

        // Setup SwipeRefreshLayout
        srlMain.setOnRefreshListener {
            mMovieAdapter.clear()
            mMovieViewModel.getPopular(1)
        }
    }

    private fun subscribeMovieList() {
        mMovieViewModel.apply {
            observe(getPopular(page), ::onMovieLiveDataChanged)
        }
    }

    private fun onMovieLiveDataChanged(data: Data<Request<Movie>>?) {
        data.let { movies ->
            when (movies!!.dataState) {
                DataState.LOADING -> {
                    isLoading = true
                    Utilities.showMessage(view!!.rootView, "Loading movies...")
                }

                DataState.SUCCESS -> {
                    resetLoadingIndicators()
                    mMovieAdapter.addMovies(movies.data!!.results)
                }

                DataState.ERROR -> {
                    resetLoadingIndicators()
                    Utilities.showMessage(view!!.rootView, "Error loading data, please try again later.", Snackbar.LENGTH_INDEFINITE)
                }
            }
        }
    }

    private fun resetLoadingIndicators() {
        isLoading = false
        srlMain.isRefreshing = false
    }

    override fun onItemClick(item: Movie) {
        Log.d(TAG, "Selected movie is " + item.title + " with an id of " + item.id)
        // TODO - Connect to Flutter
    }
}
