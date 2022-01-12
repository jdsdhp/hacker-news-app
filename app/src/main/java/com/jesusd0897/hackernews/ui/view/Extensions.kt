package com.jesusd0897.hackernews.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes

/** makes visible a view. */
fun View.visible() {
    visibility = View.VISIBLE
}

/** makes invisible a view. */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/** makes gone a view. */
fun View.gone() {
    visibility = View.GONE
}

@ColorInt
fun Context.getThemeAttrColor(@AttrRes attr: Int): Int {
    val typedArray = this.obtainStyledAttributes(intArrayOf(attr))
    return try {
        typedArray.getColor(0, 0)
    } finally {
        typedArray.recycle()
    }
}

fun Context.getThemeAttrDrawable(@AttrRes attr: Int): Drawable? {
    val typedArray = this.obtainStyledAttributes(intArrayOf(attr))
    return try {
        typedArray.getDrawable(0)
    } finally {
        typedArray.recycle()
    }
}

fun Context.getDimension(@DimenRes dimenRes: Int): Float = resources.getDimension(dimenRes)