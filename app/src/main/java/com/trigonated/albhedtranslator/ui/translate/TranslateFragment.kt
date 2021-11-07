package com.trigonated.albhedtranslator.ui.translate

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.trigonated.albhedtranslator.R
import com.trigonated.albhedtranslator.databinding.FragmentTranslateBinding
import com.trigonated.albhedtranslator.ui.misc.copyToClipboard
import com.trigonated.albhedtranslator.ui.misc.hideSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslateFragment : Fragment() {
    /** This fragment's ViewModel. */
    private val viewModel: TranslateViewModel by viewModels()

    /** Backing field for [binding]. */
    private var _binding: FragmentTranslateBinding? = null

    /** This fragment's binding. */
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Setup the UI
        _binding = FragmentTranslateBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TranslateFragment.viewModel

            inputEdittext.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    hideSoftKeyboard()
                }
            }
            inputEdittext.requestFocus()

            translationEdittext.inputType = InputType.TYPE_NULL
            translationEdittext.keyListener = null
        }

        // Observe
        viewModel.onCopyTextRequested.observe(viewLifecycleOwner) { value ->
            copyToClipboard(value, value)
            Toast.makeText(
                requireContext(),
                R.string.translate_text_copied,
                Toast.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}