package io.jmdg.themoviedb.di

import dagger.Component
import io.jmdg.themoviedb.di.modules.AppModule
import io.jmdg.themoviedb.di.modules.NetworkModule
import io.jmdg.themoviedb.di.modules.ViewModelModule
import io.jmdg.themoviedb.ui.main.MainFragment
import javax.inject.Singleton

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface Injector {
    fun inject(mainFragment: MainFragment)
}