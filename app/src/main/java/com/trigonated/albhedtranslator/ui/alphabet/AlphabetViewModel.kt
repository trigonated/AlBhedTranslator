package com.trigonated.albhedtranslator.ui.alphabet

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.trigonated.albhedtranslator.model.Repository
import com.trigonated.albhedtranslator.model.objects.AlBhedAlphabetEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class AlphabetViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    /** The data for the alphabet entries. */
    private val alphabetEntries: Flow<List<AlBhedAlphabetEntry>> = repository.alphabetEntries

    /** Backing field for [alBhedFirst]. */
    private val _alBhedFirst = MutableStateFlow<Boolean>(true)

    /** Whether al bhed characters should appear before english or not. */
    val alBhedFirst: LiveData<Boolean> = _alBhedFirst.asLiveData()

    /** The list's items. */
    val items: LiveData<List<AlBhedAlphabetEntry>> =
        alphabetEntries.combine(_alBhedFirst) { ae, abf ->
            ae.sortedBy { if (abf) it.alBhed else it.english }
        }.asLiveData()

    /**
     * Toggle between sorting by al bhed or english.
     */
    fun toggleSorting() {
        _alBhedFirst.value = !_alBhedFirst.value
    }
}