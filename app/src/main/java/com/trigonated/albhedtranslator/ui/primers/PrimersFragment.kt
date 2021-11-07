package com.trigonated.albhedtranslator.ui.primers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.trigonated.albhedtranslator.databinding.FragmentPrimersBinding
import com.trigonated.albhedtranslator.model.objects.AlBhedPrimer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrimersFragment : Fragment() {
    /** This fragment's ViewModel. */
    private val viewModel: PrimersViewModel by viewModels()

    /** Backing field for [binding]. */
    private var _binding: FragmentPrimersBinding? = null

    /** This fragment's binding. */
    private val binding get() = _binding!!

    /** The list's adapter. */
    private val listAdapter: AlBhedPrimerListAdapter get() = binding.list.adapter as AlBhedPrimerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Setup the UI
        _binding = FragmentPrimersBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@PrimersFragment.viewModel

            // Setup the list's adapter
            list.adapter = AlBhedPrimerListAdapter().apply {
                itemIsObtainedChangedListener =
                    object : AlBhedPrimerListAdapter.ItemIsObtainedChangedListener {
                        override fun onItemIsObtainedChanged(
                            item: AlBhedPrimer,
                            isObtained: Boolean
                        ) {
                            this@PrimersFragment.viewModel.setPrimerObtainedStatus(item, isObtained)
                        }
                    }
            }
        }
        setHasOptionsMenu(true)

        // Observe
        viewModel.items.observe(viewLifecycleOwner) { value ->
            listAdapter.submitList(value)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}