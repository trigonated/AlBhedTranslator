package com.trigonated.albhedtranslator.misc

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.trigonated.albhedtranslator.model.AppPreferences

private const val PREFS_NAME: String = "AlBhedTranslatorPrefs"
private const val PREF_OBTAINED_PRIMERS: String = "obtainedPrimers"

/**
 * Android runtime implementation of [AppPreferences].
 */
class AndroidAppPreferencesImpl(context: Context) : AppPreferences {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

    override var obtainedPrimers: Set<Int>
        get() {
            val value = sharedPreferences.getStringSet(PREF_OBTAINED_PRIMERS, null) ?: setOf()
            return value.mapNotNull { it.toIntOrNull() }.toSortedSet()
        }
        set(value) {
            val newValue: Set<String> = value.map { it.toString() }.toSet()

            sharedPreferences.edit(commit = true) {
                putStringSet(PREF_OBTAINED_PRIMERS, newValue)
            }
        }
}