package com.trigonated.albhedtranslator.ui.testutils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion

fun hasViewWithTextAtPosition(index: Int, text: CharSequence): ViewAssertion {
    return ViewAssertion { view, e ->
        if (view !is RecyclerView) throw e!!

        val outviews: ArrayList<View> = ArrayList()
        view.scrollToPosition(index)
        Thread.sleep(500)
        view.findViewHolderForAdapterPosition(index)!!.itemView.findViewsWithText(
            outviews,
            text,
            View.FIND_VIEWS_WITH_TEXT
        )
        assert(outviews.isNotEmpty()) { "There's no view at index $index of recyclerview that has text : $text" }
    }
}