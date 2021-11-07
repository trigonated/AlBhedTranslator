package com.trigonated.albhedtranslator.ui.misc

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat

fun Context.getText(@StringRes id: Int, vararg args: Any?): CharSequence {
    val html = String.format(getString(id), *args)
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}
