package com.trigonated.albhedtranslator.ui.translate

import androidx.lifecycle.*
import com.trigonated.albhedtranslator.misc.EventLiveData
import com.trigonated.albhedtranslator.model.Repository
import com.trigonated.albhedtranslator.model.objects.AlBhedString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    /** The "input" text (what the user types to be translated). */
    val inputText = MutableLiveData<AlBhedString>(AlBhedString())

    /** Backing field for [inputIsAlBhed]. */
    private val _inputIsAlBhed = MutableStateFlow(true)

    /** Whether [inputText] is al bhed (to translate to english) or vice-versa. */
    val inputIsAlBhed: LiveData<Boolean> = _inputIsAlBhed.asLiveData()

    /** Whether the user is writing al bhed (works similarly to a "bold" button on text editors). */
    val writingAlBhed = MutableLiveData<Boolean>(false)

    /** Whether al bhed characters are to be detected automatically. */
    val detectAlBhedAutomatically = MutableLiveData<Boolean>(false)

    /** The tranlation of [inputText]. */
    val translation: LiveData<String> = MediatorLiveData<String>().apply {
        fun func(): String? {
            val input: AlBhedString = inputText.value ?: return null
            return if (_inputIsAlBhed.value) { // AlBhed to English
                val translationMode: Repository.TranslationMode =
                    if (detectAlBhedAutomatically.value == true)
                        Repository.TranslationMode.FromObtainedPrimers
                    else
                        Repository.TranslationMode.Custom
                repository.translateToEnglish(input, translationMode)
            } else { // English to AlBhed
                repository.translateToAlBhed(input)
            }
        }
        addSource(inputText) { setValue(func()) }
        addSource(detectAlBhedAutomatically) { setValue(func()) }
    }

    /**
     * Fired when the user requests to copy the text to the clipbboard.
     */
    val onCopyTextRequested = EventLiveData<String>()

    /**
     * Swap the translation languages (al bhed -> english to english -> al bhed).
     */
    fun swapLanguages() {
        val newInputText = AlBhedString(translation.value ?: "")
        _inputIsAlBhed.value = !_inputIsAlBhed.value
        inputText.value = newInputText
    }

    /**
     * Copy the translation to the clipboard.
     */
    fun copyTranslation() {
        onCopyTextRequested.call(translation.value ?: "")
    }
}