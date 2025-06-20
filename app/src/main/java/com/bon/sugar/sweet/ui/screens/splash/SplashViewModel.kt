package com.bon.sugar.sweet.ui.screens.splash

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bon.sugar.sweet.data.AimBuilder
import com.bon.sugar.sweet.domain.AppsRepository
import com.bon.sugar.sweet.domain.DataStoreRepository
import com.bon.sugar.sweet.domain.GaidRepository
import com.bon.sugar.sweet.domain.InstallReferrer
import com.bon.sugar.sweet.domain.LoadingState
import com.bon.sugar.sweet.domain.NetworkCheckerRepository
import com.bon.sugar.sweet.domain.PushServiceInitializer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkCheckerRepository: NetworkCheckerRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val installReferrer: InstallReferrer,
    private val appsRepository: AppsRepository,
    private val gaidRepository: GaidRepository,
    private val pushServiceInitializer: PushServiceInitializer
) : ViewModel() {

    private val permissionState = MutableStateFlow<Boolean?>(null)

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
                        proceedWithContent(context)
                    }
                } else {
                    if (adbFromStorage) {
                        mutableLiveState.emit(LoadingState.MenuState)
                    } else {
                        proceedWithContent(context)
                    }
                }
            } else {
                mutableLiveState.emit(LoadingState.NoNetworkState)
            }
        }
    }

    private suspend fun proceedWithContent(context: Context) {
        val urlFromStorage = dataStoreRepository.getUrl()
        if (urlFromStorage == "EMPTY") {
            val referrer = installReferrer.fetchReferrer()
            val gaid = gaidRepository.getGaid()
            pushServiceInitializer.initializePushService(gaid, context)
            val uuid = appsRepository.provideDIUU(context)
            val appsdata = appsRepository.provideFlyer(context, "faEAUodKKdvasvPWy3HrrV")

            val finalUrl = AimBuilder().setBrinID(gaid).setAppsIdentif(uuid).setAppsData(appsdata)
                .setInstallReferrer(referrer.toString()).build()
            Log.v("123123", finalUrl)
            permissionState.collect {
                if (it != null) {
                    mutableLiveState.emit(LoadingState.ContentState(finalUrl))
                }
            }
        } else {
            Log.v("123123", "from storage:$urlFromStorage")
            mutableLiveState.emit(LoadingState.ContentState(urlFromStorage))
        }
    }


    fun updatePermissionState(isGranted: Boolean) {
        viewModelScope.launch {
            permissionState.emit(isGranted)
        }
    }
}