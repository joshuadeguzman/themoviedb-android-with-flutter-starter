package io.jmdg.themoviedb.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import io.jmdg.themoviedb.R
import io.jmdg.themoviedb.adapters.MovieAdapter
import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.data.models.Request
import io.jmdg.themoviedb.utils.ViewModelFactory
import io.jmdg.themoviedb.utils.extensions.*
import io.jmdg.themoviedb.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mMovieAdapter: MovieAdapter
    private var page = 1

    companion object {
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
        mMovieAdapter = MovieAdapter(context!!)
        rvMovies.layoutManager = GridLayoutManager(context, 2)
        rvMovies.adapter = mMovieAdapter
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

                }

                DataState.SUCCESS -> {
                    mMovieAdapter.setMovies(movies.data!!.results)
                }

                DataState.ERROR -> {

                }
            }
        }
    }
}