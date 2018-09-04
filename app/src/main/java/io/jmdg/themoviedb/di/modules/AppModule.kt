package io.jmdg.themoviedb.di.modules

import dagger.Module
import dagger.Provides
import io.jmdg.themoviedb.MovieDbApp
import javax.inject.Singleton

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

@Module
class AppModule(private val movieDbApp: MovieDbApp) {
    @Provides
    @Singleton
    fun provideApp(): MovieDbApp = movieDbApp
}