package com.rjhwork.mycompany.opggcloneapp.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

internal fun ImageView.clear() = Glide.with(context).clear(this)

internal fun ImageView.load(url: String?) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

internal fun ImageView.loadCircle(url: String?) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}
