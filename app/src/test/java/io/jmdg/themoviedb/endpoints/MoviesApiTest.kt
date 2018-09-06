package io.jmdg.themoviedb.endpoints

import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.data.models.Request
import io.jmdg.themoviedb.data.remote.api.MovieApi
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Created by Joshua de Guzman on 06/09/2018.
 */

internal class MoviesApiTest : BaseApiTest() {
    private lateinit var movieApi: MovieApi

    @Before
    fun setUp() {
        movieApi = MovieApi::class.java.getRetrofitApi(server)
    }

    @Test
    fun getPopularMovies() {
        server.enqueue(createMockResponse(readFromFile("get_movies.json")))

        val subscriber = movieApi.getPopularMovies(1).test()
        subscriber.assertSubscribed()
        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValueCount(1)
        subscriber.assertValue { it.page == 1 }
        subscriber.assertValue { it.results.count() == 20 }
    }
}