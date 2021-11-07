package com.trigonated.albhedtranslator.ui.misc

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.trigonated.albhedtranslator.model.objects.AlBhedString
import com.trigonated.albhedtranslator.ui.generic.AlBhedEditText
import com.trigonated.albhedtranslator.ui.generic.CheckableImageButton

@BindingAdapter("alBhedText")
fun bindAlBhedEditTextAlBhedText(view: AlBhedEditText, value: AlBhedString?) {
    view.alBhedText = value ?: AlBhedString()
}

@InverseBindingAdapter(attribute = "alBhedText")
fun inverseBindAlBhedEditTextAlBhedText(view: AlBhedEditText): AlBhedString {
    return view.alBhedText
}

@BindingAdapter("alBhedTextAttrChanged")
fun setAlBhedTextListeners(
    view: AlBhedEditText,
    attrChange: InverseBindingListener
) {
    view.addTextChangedListener { attrChange.onChange() }
}

@BindingAdapter("writingAlBhed")
fun bindAlBhedEditTextWritingAlBhed(view: AlBhedEditText, value: Boolean?) {
    view.writingAlBhed = value ?: false
}

@InverseBindingAdapter(attribute = "writingAlBhed")
fun inverseBindAlBhedEditTextWritingAlBhed(view: AlBhedEditText): Boolean {
    return view.writingAlBhed
}

@BindingAdapter("writingAlBhedAttrChanged")
fun setWritingAlBhedListeners(
    view: AlBhedEditText,
    attrChange: InverseBindingListener
) {
    view.addOnWritingAlBhedChangedListener { _, _ -> attrChange.onChange() }
}

@BindingAdapter("checked")
fun bindCheckableImageButtonChecked(view: CheckableImageButton, value: Boolean?) {
    view.isChecked = value ?: false
}

@InverseBindingAdapter(attribute = "checked")
fun inverseBindCheckableImageButtonChecked(view: CheckableImageButton): Boolean {
    return view.isChecked
}

@BindingAdapter("checkedAttrChanged")
fun setCheckableImageButtonListeners(
    view: CheckableImageButton,
    attrChange: InverseBindingListener
) {
    view.setOnClickListener {
        view.toggle()
        attrChange.onChange()
    }
}

/**
 * Binds a [String] to a [TextView], displaying the string as html text.
 * @param view The View to bind to.
 * @param htmlText The html that will be bound.
 */
@BindingAdapter("htmlText")
fun bindTextViewHtmlText(view: TextView, htmlText: String?) {
    if (htmlText != null) {
        @Suppress("DEPRECATION")
        view.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
        else
            Html.fromHtml(htmlText)
    } else {
        view.text = null
    }
}