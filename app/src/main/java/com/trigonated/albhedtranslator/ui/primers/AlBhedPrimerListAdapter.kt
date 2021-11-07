package com.trigonated.albhedtranslator.ui.primers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trigonated.albhedtranslator.R
import com.trigonated.albhedtranslator.databinding.ListItemPrimerBinding
import com.trigonated.albhedtranslator.model.objects.AlBhedPrimer
import com.trigonated.albhedtranslator.ui.misc.getText

/**
 * List adapter for [AlBhedPrimer] objects.
 */
class AlBhedPrimerListAdapter :
    ListAdapter<AlBhedPrimer, RecyclerView.ViewHolder>(
        AlBhedPrimerDiffCallback()
    ) {
    /** Listener for changes in the "obtained" CheckBox. */
    var itemIsObtainedChangedListener: ItemIsObtainedChangedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlBhedPrimerViewHolder(
            ListItemPrimerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as AlBhedPrimerViewHolder).bind(item)
    }

    inner class AlBhedPrimerViewHolder(
        private val binding: ListItemPrimerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Setup the listener
            binding.obtainedCheckbox.setOnCheckedChangeListener { _, isChecked ->
                binding.item?.let { item ->
                    if (isChecked != item.isObtained) {
                        itemIsObtainedChangedListener?.onItemIsObtainedChanged(item, isChecked)
                    }
                }
            }
        }

        /**
         * Bind the [item] to this ViewHolder.
         * @param item The item to bind.
         */
        fun bind(item: AlBhedPrimer) {
            binding.apply {
                this.item = item
                translationText.text = root.context.getText(
                    R.string.primers_translation,
                    item.alBhedChar,
                    item.englishChar
                )
                executePendingBindings()
            }
        }
    }

    /**
     * Listener for changes in the "obtained" CheckBox.
     */
    interface ItemIsObtainedChangedListener {
        fun onItemIsObtainedChanged(item: AlBhedPrimer, isObtained: Boolean)
    }
}

/**
 * Diff class for [AlBhedPrimer] objects.
 */
private class AlBhedPrimerDiffCallback :
    DiffUtil.ItemCallback<AlBhedPrimer>() {

    override fun areItemsTheSame(
        oldItem: AlBhedPrimer,
        newItem: AlBhedPrimer
    ): Boolean {
        return oldItem.volume == newItem.volume
    }

    override fun areContentsTheSame(
        oldItem: AlBhedPrimer,
        newItem: AlBhedPrimer
    ): Boolean {
        return oldItem == newItem
    }
}