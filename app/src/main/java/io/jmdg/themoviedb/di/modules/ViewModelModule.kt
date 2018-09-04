package io.jmdg.themoviedb.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.jmdg.themoviedb.utils.ViewModelFactory
import io.jmdg.themoviedb.utils.extensions.ViewModelKey
import io.jmdg.themoviedb.viewmodel.MovieViewModel

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun bindMovieFragment(movieViewModel: MovieViewModel): ViewModel

}