package io.jmdg.themoviedb.utils.extensions

import androidx.fragment.app.Fragment
import io.jmdg.themoviedb.MovieDbApp
import io.jmdg.themoviedb.di.Injector

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

val Fragment.app: MovieDbApp get() = activity!!.application as MovieDbApp

fun Fragment.getAppInjector(): Injector = (app).injector