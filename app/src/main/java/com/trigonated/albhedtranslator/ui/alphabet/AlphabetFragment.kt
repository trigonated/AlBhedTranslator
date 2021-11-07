package com.trigonated.albhedtranslator.ui.alphabet

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.trigonated.albhedtranslator.R
import com.trigonated.albhedtranslator.databinding.FragmentAlphabetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlphabetFragment : Fragment() {
    /** This fragment's ViewModel. */
    private val viewModel: AlphabetViewModel by viewModels()

    /** Backing field for [binding]. */
    private var _binding: FragmentAlphabetBinding? = null

    /** This fragment's binding. */
    private val binding get() = _binding!!

    /** The list's adapter. */
    private val listAdapter: AlBhedAlphabetEntryListAdapter get() = binding.list.adapter as AlBhedAlphabetEntryListAdapter

    /** The "sort" menu item. */
    private var sortMenuItem: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Setup the UI
        _binding = FragmentAlphabetBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AlphabetFragment.viewModel

            // Setup the list's adapter
            list.adapter = AlBhedAlphabetEntryListAdapter()
        }
        setHasOptionsMenu(true)

        // Observe
        viewModel.items.observe(viewLifecycleOwner) { value ->
            listAdapter.submitList(value)
        }
        viewModel.alBhedFirst.observe(viewLifecycleOwner) {
            updateSortMenuItem()
            // Update the list with the new sorting
            listAdapter.alBhedFirst = viewModel.alBhedFirst.value ?: true
            listAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_alphabet, menu)
        sortMenuItem = menu.findItem(R.id.action_sort)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sort) {
            viewModel.toggleSorting()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        sortMenuItem = null
    }

    /**
     * Updates the sort menu item with the appropriate title.
     */
    private fun updateSortMenuItem() {
        sortMenuItem?.title = if (viewModel.alBhedFirst.value != false)
            getString(R.string.alphabet_menu_sort_al_en)
        else
            getString(R.string.alphabet_menu_sort_en_al)
    }
}