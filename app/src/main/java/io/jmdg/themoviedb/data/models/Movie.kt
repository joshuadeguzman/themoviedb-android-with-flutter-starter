package io.jmdg.themoviedb.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

data class Movie(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("vote_average")
        val voteAverage: Float
)