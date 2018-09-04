package io.jmdg.themoviedb.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import io.jmdg.themoviedb.R

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

fun ImageView.loadImageFromUrl(url: String) {
    Glide
            .with(context)
            .load(url)
            .apply(
                    RequestOptions()
                            .placeholder(R.mipmap.ic_launcher)
            )
            .into(this)
}

fun ImageView.loadImageFromUrl(url: String, radius: Int) {
    Glide
            .with(context)
            .load(url)

            .apply(
                    RequestOptions()
                            .placeholder(R.mipmap.ic_launcher)
                            .transforms(CenterCrop(), RoundedCorners(radius))
            )
            .into(this)
}