package com.trigonated.albhedtranslator.ui.misc

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import androidx.fragment.app.Fragment

/**
 * Hide the soft keyboard.
 *
 * Note: This calls [androidx.fragment.app.Fragment.requireActivity].
 */
fun Fragment.hideSoftKeyboard() {
    InputMethodUtils.hideSoftKeyboard(requireActivity())
}

/**
 * Copy [text] to the clipoard.
 *
 * Note: This calls [androidx.fragment.app.Fragment.requireActivity].
 */
fun Fragment.copyToClipboard(label: String, text: String) {
    val clipboardManager =
        requireActivity().getSystemService(Activity.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text))
}