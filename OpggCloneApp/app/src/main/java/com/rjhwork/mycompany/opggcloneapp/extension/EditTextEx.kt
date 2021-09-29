package com.rjhwork.mycompany.opggcloneapp.extension

import android.graphics.drawable.Drawable
import android.widget.EditText

fun EditText.hideEndDrawable() {
    this.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
}

fun EditText.showEndDrawable(drawable: Drawable?) {
    drawable ?: return
    this.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
}

