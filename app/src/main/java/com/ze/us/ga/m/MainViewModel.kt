package com.ze.us.ga.m

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ze.us.ga.m.domain.PostErrorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tr.tp.tech.inves.data.ErrorMessage
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val postErrorApi: PostErrorRepository) :
    ViewModel() {

    fun sendError(errorMessage: ErrorMessage) {
        viewModelScope.launch {
            postErrorApi.postError(errorMessage)
        }
    }

}