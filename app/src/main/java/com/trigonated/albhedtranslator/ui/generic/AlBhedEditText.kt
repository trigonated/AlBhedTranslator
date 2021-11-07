package com.trigonated.albhedtranslator.ui.generic

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.trigonated.albhedtranslator.model.objects.AlBhedString

/**
 * [EditText] with support for [AlBhedString].
 */
class AlBhedEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputEditText(context, attrs, defStyleAttr) {
    /** Whether the next [onTextChanged] event should be ignored. */
    private var ignoreOnTextChanged: Boolean = false

    /** The [AlBhedString] text. */
    var alBhedText: AlBhedString
        get() = AlBhedString.fromEditable(text)
        set(value) {
            if (alBhedText == value) return
            ignoreOnTextChanged = true
            text = value.toEditable()
        }

    /** Backing field for [writingAlBhed]. */
    private var _writingAlBhed: Boolean = false

    /** Whether the user is currently writing al bhed. */
    var writingAlBhed: Boolean
        get() = _writingAlBhed
        set(value) {
            if (_writingAlBhed == value) return
            _writingAlBhed = value

            // If some text was selected mark/unmark it as al bhed
            val oldSelectionStart: Int = selectionStart
            val oldSelectionEnd: Int = selectionEnd
            if (oldSelectionStart != oldSelectionEnd) {
                ignoreOnTextChanged = true
                alBhedText = alBhedText.formatPart(selectionStart, selectionEnd, value)
                setSelection(oldSelectionStart, oldSelectionEnd)

                callOnWritingAlBhedChangedListeners()
            }
        }

    /** Listeners for when [writingAlBhed] changes. */
    private val onWritingAlBhedChangedListeners: MutableList<(view: AlBhedEditText, value: Boolean) -> Unit> =
        mutableListOf()

    init {
        text = SpannableStringBuilder("")
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        if (ignoreOnTextChanged) {
            ignoreOnTextChanged = false
            return
        }

        if (writingAlBhed) {
            this.text!!.setSpan(
                StyleSpan(Typeface.BOLD),
                start,
                start + lengthAfter,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    fun addOnWritingAlBhedChangedListener(listener: (view: AlBhedEditText, value: Boolean) -> Unit) {
        onWritingAlBhedChangedListeners.add(listener)
    }

    fun removeOnWritingAlBhedChangedListener(listener: (view: AlBhedEditText, value: Boolean) -> Unit) {
        onWritingAlBhedChangedListeners.remove(listener)
    }

    private fun callOnWritingAlBhedChangedListeners() {
        onWritingAlBhedChangedListeners.forEach { it(this, writingAlBhed) }
    }
}