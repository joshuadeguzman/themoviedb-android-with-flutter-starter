package io.jmdg.themoviedb.helpers


/**
 * Created by Joshua de Guzman on 2019-07-11.
 */

interface ItemClickListener<T> {
    fun onItemClick(item : T)
}