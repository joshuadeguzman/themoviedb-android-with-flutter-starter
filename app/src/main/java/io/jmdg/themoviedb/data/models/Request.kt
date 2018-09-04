package io.jmdg.themoviedb.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

data class Request<T>(
        @SerializedName("page")
        val page: Int,
        @SerializedName("results")
        val results: List<T>
)