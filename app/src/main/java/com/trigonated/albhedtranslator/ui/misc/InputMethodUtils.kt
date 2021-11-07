package com.trigonated.albhedtranslator.ui.misc

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

object InputMethodUtils {
    fun hideSoftKeyboard(activity: Activity, implicitOnly: Boolean = false) {
        val windowToken = activity.findViewById<View>(android.R.id.content)?.windowToken ?: return
        val inputMethodService =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodService.hideSoftInputFromWindow(
            windowToken,
            if (implicitOnly) InputMethodManager.HIDE_IMPLICIT_ONLY else 0
        )
    }
}