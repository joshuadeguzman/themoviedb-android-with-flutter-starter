package io.jmdg.themoviedb

import android.app.Application
import io.jmdg.themoviedb.di.DaggerInjector
import io.jmdg.themoviedb.di.Injector
import io.jmdg.themoviedb.di.modules.AppModule
import timber.log.Timber

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

class MovieDbApp: Application(){
    lateinit var injector: Injector

    override fun onCreate() {
        super.onCreate()

        // Dagger Injector
        injector = DaggerInjector
                .builder()
                .appModule(AppModule(this))
                .build()

        // Init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}