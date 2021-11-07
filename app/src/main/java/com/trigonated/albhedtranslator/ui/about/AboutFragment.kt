package com.trigonated.albhedtranslator.ui.about

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.trigonated.albhedtranslator.databinding.FragmentAboutBinding
import com.trigonated.albhedtranslator.ui.misc.IntentUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment() {
    /** This fragment's ViewModel. */
    private val viewModel: AboutViewModel by viewModels()

    /** Backing field for [binding]. */
    private var _binding: FragmentAboutBinding? = null

    /** This fragment's binding. */
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Setup the UI
        _binding = FragmentAboutBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AboutFragment.viewModel
        }

        // Observe the ViewModel
        viewModel.onOpenUrlRequested.observe(viewLifecycleOwner) { url ->
            IntentUtils.openUrl(requireContext(), url)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}