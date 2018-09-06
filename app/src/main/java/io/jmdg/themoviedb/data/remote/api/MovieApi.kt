package io.jmdg.themoviedb.data.remote.api

import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.data.models.Request
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Observable<Request<Movie>>

    @GET("search/movie")
    fun getMovies(@Query("query") query: String,
                  @Query("page") page: Int): Observable<Request<Movie>>
}