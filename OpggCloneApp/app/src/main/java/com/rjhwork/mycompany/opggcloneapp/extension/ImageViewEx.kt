package com.rjhwork.mycompany.opggcloneapp.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.rjhwork.mycompany.opggcloneapp.data.mapper.getOrnnItemImage

private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

internal fun ImageView.clear() = Glide.with(context).clear(this)

internal fun ImageView.load(url: String?) {
    url?.let { itemUrl ->
        if(itemUrl.contains(".png").not()) {
            setImageDrawable(getOrnnItemImage(itemUrl)?.let {
                ContextCompat.getDrawable(context, it)
            })
            return
        }

        Glide.with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}

internal fun ImageView.loadCircle(url: String?) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}
