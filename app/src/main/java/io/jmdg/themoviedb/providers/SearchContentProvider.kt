package io.jmdg.themoviedb.providers

import android.content.SearchRecentSuggestionsProvider


/**
 * Created by Joshua de Guzman on 05/09/2018.
 */

// More on: https://developer.android.com/guide/topics/search/adding-recent-query-suggestions

class SearchContentProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        val AUTHORITY = "io.jmdg.themoviedb.suggestionprovider"
        val MODE = DATABASE_MODE_QUERIES
    }
}