package io.jmdg.themoviedb.utils.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

enum class DataState { LOADING, SUCCESS, ERROR }

data class Data<out T> constructor(val dataState: DataState, val data: T? = null)

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}