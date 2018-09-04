package io.jmdg.themoviedb.data.remote

import com.google.gson.Gson
import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.data.models.Request
import io.jmdg.themoviedb.data.remote.api.MovieApi
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

@Singleton
class MovieRepository @Inject constructor(private val movieApi: MovieApi) {
    fun getPopularMovies(page: Int): Observable<Request<Movie>> =
            movieApi.getPopularMovies(page)
                    .doOnNext { Timber.d(Gson().toJson(it.results)) }
                    .doOnError { Timber.e(it) }
}