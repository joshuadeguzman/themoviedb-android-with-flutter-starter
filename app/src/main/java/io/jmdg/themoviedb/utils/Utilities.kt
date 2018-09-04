package io.jmdg.themoviedb.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


/**
 * Created by Joshua de Guzman on 05/09/2018.
 */

object Utilities {
    fun showMessage(parentLayout: View, message: String, duration: Int = Snackbar.LENGTH_LONG, actionTitle: String = "DISMISS") {
        Snackbar.make(parentLayout, message, duration)
                .setAction(actionTitle) { }
                .show()
    }
}