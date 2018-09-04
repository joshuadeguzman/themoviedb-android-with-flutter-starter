package io.jmdg.themoviedb.utils.extensions

import android.app.Activity
import io.jmdg.themoviedb.MovieDbApp
import io.jmdg.themoviedb.di.Injector

/**
 * Created by Joshua de Guzman on 05/09/2018.
 */

val Activity.app: MovieDbApp get() = application as MovieDbApp

fun Activity.getAppInjector(): Injector = (app).injector