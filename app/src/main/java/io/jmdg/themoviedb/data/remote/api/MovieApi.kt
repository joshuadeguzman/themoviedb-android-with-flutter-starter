package io.jmdg.themoviedb.data.remote.api

import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.data.models.Request
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

interface MovieApi {
    @POST("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Observable<Request<Movie>>
}