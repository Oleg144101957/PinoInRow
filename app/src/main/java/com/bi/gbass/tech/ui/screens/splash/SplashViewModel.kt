package com.bi.gbass.tech.ui.screens.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bi.gbass.tech.domain.DataStoreRepository
import com.bi.gbass.tech.domain.LoadingState
import com.bi.gbass.tech.domain.NetworkCheckerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkCheckerRepository: NetworkCheckerRepository,
    private val dataStoreRepository: DataStoreRepository

) : ViewModel() {
    private val mutableLiveState = MutableStateFlow<LoadingState>(LoadingState.InitState)
    val liveState: StateFlow<LoadingState> = mutableLiveState

    private val permissionState = MutableStateFlow<Boolean?>(null)

    fun load(context: Context) {
        viewModelScope.launch {
            val network = networkCheckerRepository.isConnectionAvailable()
            if (network) {
                val url = "https://joinfunplace.com/r7z8KkNQ"
                dataStoreRepository.saveUrl(url)
                mutableLiveState.emit(LoadingState.ContentState(url))
            } else {
                mutableLiveState.emit(LoadingState.NoNetworkState)
            }

        }
    }

    fun updatePermissionState(isGranted: Boolean) {
        viewModelScope.launch {
            permissionState.emit(isGranted)
        }
    }

    companion object {
        private const val EMPTY = "EMPTY"
    }
}