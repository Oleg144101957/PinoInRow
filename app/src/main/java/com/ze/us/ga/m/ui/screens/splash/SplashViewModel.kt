package com.ze.us.ga.m.ui.screens.splash

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ze.us.ga.m.data.AimBuilder
import com.ze.us.ga.m.domain.AppsRepository
import com.ze.us.ga.m.domain.DataStoreRepository
import com.ze.us.ga.m.domain.GaidRepository
import com.ze.us.ga.m.domain.InstallReferrer
import com.ze.us.ga.m.domain.LoadingState
import com.ze.us.ga.m.domain.NetworkCheckerRepository
import com.ze.us.ga.m.domain.PushServiceInitializer
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

            if (network) {
                proceedWithContent(context)
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
            val appsdata = appsRepository.provideFlyer(context, "7BCEv78obnezkrZQhtZZ2e")

            val finalUrl = AimBuilder()
                .setBrinID(gaid)
                .setAppsIdentif(uuid)
                .setAppsData(appsdata)
                .setInstallReferrer(referrer.toString())
                .build()

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
