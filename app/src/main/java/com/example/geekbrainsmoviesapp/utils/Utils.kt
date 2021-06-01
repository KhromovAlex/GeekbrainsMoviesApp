package com.example.geekbrainsmoviesapp.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(
    @StringRes text: Int,
    length: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(this, resources.getString(text), length).show()
}

fun View.show() : View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide() : View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}
