package com.trigonated.albhedtranslator.model.objects

import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.core.text.getSpans
import kotlin.math.max
import kotlin.math.min

/**
 * A string containing Al Bhed characters. Which characters are Al Bhed is defined by [alBhedParts].
 */
data class AlBhedString(
    /** The characters. */
    val string: String,
    /** Which characters are Al Bhed or not. Has the same length as [string]. */
    val alBhedParts: List<Boolean>
) {
    companion object {
        /**
         * Create an [AlBhedString] from an [editable].
         *
         * @param editable The [Editable] to create the string from. If null, an empty string is
         * created.
         */
        fun fromEditable(editable: Editable?): AlBhedString {
            if (editable == null) return AlBhedString()

            return AlBhedString(
                editable.toString(),
                mutableListOf<Boolean>().apply {
                    // Fill the list as non-albhed
                    repeat(editable.length) { add(false) }

                    // Find the bold characters in the editable and mark those as al bhed
                    var spanStart: Int
                    var spanEnd: Int
                    editable.getSpans<StyleSpan>().forEach { span ->
                        spanStart = editable.getSpanStart(span)
                        spanEnd = editable.getSpanEnd(span)
                        for (i in spanStart until spanEnd) {
                            this[i] = (span.style == Typeface.BOLD)
                        }
                    }
                }
            )
        }
    }

    /**
     * Create an [AlBhedString] from [str]. The entire string is marked as non-albhed.
     *
     * @param str The string to create from.
     */
    constructor(str: String) : this(
        str,
        mutableListOf<Boolean>().apply {
            repeat(str.length) { add(false) }
        }
    )

    constructor() : this("", emptyList())

    /**
     * Create an [Editable] from this [AlBhedString]. Al Bhed characters are styled as bold.
     */
    fun toEditable(): Editable {
        val str = SpannableStringBuilder(string)
        alBhedParts.forEachIndexed { i, isAlBhed ->
            if (isAlBhed) {
                str.setSpan(
                    StyleSpan(Typeface.BOLD),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return str
    }

    /**
     * Copy this [AlBhedString] into a new one, with the range [start] to [end] formatted as al bhed
     * or english, depending on [formatAsAlBhed].
     *
     * @param start The start index of the formatted part.
     * @param end The end index of the formatted part.
     * @param formatAsAlBhed Whether the range gets formatted as al bhed or not.
     */
    fun formatPart(start: Int, end: Int, formatAsAlBhed: Boolean): AlBhedString {
        val newAlBhedParts = alBhedParts.toMutableList()
        for (i in max(start, 0) until min(end, string.length)) {
            newAlBhedParts[i] = formatAsAlBhed
        }
        return AlBhedString(
            string,
            newAlBhedParts
        )
    }

    /**
     * Copy this [AlBhedString] into a new one, appending [str] at the end. [isAlBhed] defines
     * whether the appended part is al bhed or not.
     *
     * @param str The string to append.
     * @param isAlBhed Whether [str] is al bhed or not.
     */
    fun append(str: String, isAlBhed: Boolean): AlBhedString {
        return AlBhedString(
            string + str,
            alBhedParts + isAlBhed
        )
    }

    /**
     * Return whether the char at [index] is marked as al bhed or not.
     *
     * @param index The index of the char to check.
     */
    fun isCharAlBhed(index: Int): Boolean {
        return (alBhedParts[index])
    }

    operator fun plus(str: AlBhedString): AlBhedString {
        return AlBhedString(
            string + str.string,
            alBhedParts + str.alBhedParts
        )
    }

    override fun equals(other: Any?): Boolean {
        return if (other is AlBhedString) {
            return ((string == other.string) && (alBhedParts == other.alBhedParts))
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + alBhedParts.hashCode()
        return result
    }
}