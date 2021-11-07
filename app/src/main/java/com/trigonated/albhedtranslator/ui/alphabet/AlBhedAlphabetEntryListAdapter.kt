package com.trigonated.albhedtranslator.ui.alphabet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trigonated.albhedtranslator.R
import com.trigonated.albhedtranslator.databinding.ListItemAlphabetEntryBinding
import com.trigonated.albhedtranslator.model.objects.AlBhedAlphabetEntry
import com.trigonated.albhedtranslator.ui.misc.getText

/**
 * List adapter for [AlBhedAlphabetEntry] objects.
 */
class AlBhedAlphabetEntryListAdapter(
    /** Whether to sort by al bhed or english first. */
    var alBhedFirst: Boolean = true
) :
    ListAdapter<AlBhedAlphabetEntry, RecyclerView.ViewHolder>(
        AlBhedAlphabetEntryDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlBhedAlphabetEntryViewHolder(
            ListItemAlphabetEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as AlBhedAlphabetEntryViewHolder).bind(item)
    }

    inner class AlBhedAlphabetEntryViewHolder(
        private val binding: ListItemAlphabetEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind the [item] to this ViewHolder.
         * @param item The item to bind.
         */
        fun bind(item: AlBhedAlphabetEntry) {
            binding.apply {
                this.item = item
                translationText.text = root.context.getText(
                    if (alBhedFirst)
                        R.string.alphabet_item_albhed_to_english
                    else
                        R.string.alphabet_item_english_to_albhed,
                    item.alBhed, item.english
                )
                executePendingBindings()
            }
        }
    }
}

/**
 * Diff class for [AlBhedAlphabetEntry] objects.
 */
private class AlBhedAlphabetEntryDiffCallback :
    DiffUtil.ItemCallback<AlBhedAlphabetEntry>() {

    override fun areItemsTheSame(
        oldItem: AlBhedAlphabetEntry,
        newItem: AlBhedAlphabetEntry
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AlBhedAlphabetEntry,
        newItem: AlBhedAlphabetEntry
    ): Boolean {
        return oldItem == newItem
    }
}