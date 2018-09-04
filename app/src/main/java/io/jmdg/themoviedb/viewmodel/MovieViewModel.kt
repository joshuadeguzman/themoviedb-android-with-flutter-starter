package io.jmdg.themoviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.data.models.Request
import io.jmdg.themoviedb.data.remote.MovieRepository
import io.jmdg.themoviedb.utils.extensions.Data
import io.jmdg.themoviedb.utils.extensions.DataState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()
    private var movieListLiveData = MutableLiveData<Data<Request<Movie>>>()

    fun getPopular(page: Int): MutableLiveData<Data<Request<Movie>>> {
        compositeDisposable.add(movieRepository.getPopularMovies(page)
                .doOnSubscribe {
                    movieListLiveData.postValue(Data(DataState.LOADING, null))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieList ->
                    movieListLiveData.postValue(Data(DataState.SUCCESS, movieList))
                }, { movieListLiveData.postValue(Data(DataState.ERROR, null)) })
        )
        return movieListLiveData
    }

    fun getMovies(query: String, page: Int): MutableLiveData<Data<Request<Movie>>> {
        compositeDisposable.add(movieRepository.getMovies(query, page)
                .doOnSubscribe {
                    movieListLiveData.postValue(Data(DataState.LOADING, null))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieList ->
                    movieListLiveData.postValue(Data(DataState.SUCCESS, movieList))
                }, { movieListLiveData.postValue(Data(DataState.ERROR, null)) })
        )
        return movieListLiveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}