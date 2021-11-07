package com.trigonated.albhedtranslator.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trigonated.albhedtranslator.BuildConfig
import com.trigonated.albhedtranslator.misc.EventLiveData
import com.trigonated.albhedtranslator.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    /** The application version. */
    val version: LiveData<String> = MutableLiveData(BuildConfig.VERSION_NAME)

    /** Fired when the viewmodel wants to open an url in the browser. */
    val onOpenUrlRequested = EventLiveData<String>()

    /** Open the project's GitHub page. */
    fun openGitHubPage() = onOpenUrlRequested.call(BuildConfig.GITHUB_PAGE_URL)
}