package io.jmdg.themoviedb.endpoints

import io.jmdg.themoviedb.data.remote.api.MovieApi
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
        server.enqueue(createMockResponse(readFromFile("get_popular_movies.json")))

        val subscriber = movieApi.getPopularMovies(1).test()
        subscriber.assertSubscribed()
        subscriber.assertComplete()
        subscriber.assertValueCount(1)
        subscriber.assertNoErrors()
        subscriber.assertValue { it.page == 1 }
        subscriber.assertValue { it.results.count() == 20 }
    }

    @Test
    fun getMovies() {
        server.enqueue(createMockResponse(readFromFile("get_movies.json")))

        val subscriber = movieApi.getMovies("foo", 1).test()
        subscriber.assertSubscribed()
        subscriber.assertComplete()
        subscriber.assertValueCount(1)
        subscriber.assertNoErrors()
        subscriber.assertValue { it.results.first().id == 9090 }
        subscriber.assertValue { it.results.first().title == "To Wong Foo, Thanks for Everything! Julie Newmar" }
        subscriber.assertValue { it.results.first().posterPath == "/xIDEoG9FQGmMCh5XsbkvSuD8WrW.jpg" }
        subscriber.assertValue { it.results.first().voteAverage == 6.7f }
    }
}