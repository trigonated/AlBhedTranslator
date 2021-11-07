package com.trigonated.albhedtranslator.ui.primers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.trigonated.albhedtranslator.model.Repository
import com.trigonated.albhedtranslator.model.objects.AlBhedPrimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrimersViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    /** The list's items */
    val items: LiveData<List<AlBhedPrimer>> = repository.primers.asLiveData()

    /**
     * Set the "obtained" status of a primer [item].
     *
     * @param item The primer to change
     * @param isObtained The new status
     */
    fun setPrimerObtainedStatus(item: AlBhedPrimer, isObtained: Boolean) {
        if (item.isObtained != isObtained) {
            viewModelScope.launch {
                repository.setPrimerObtained(item, isObtained)
            }
        }
    }
}