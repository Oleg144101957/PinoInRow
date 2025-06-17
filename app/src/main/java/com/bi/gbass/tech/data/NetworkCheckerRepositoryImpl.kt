package com.bi.gbass.tech.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.bi.gbass.tech.domain.NetworkCheckerRepository
import javax.inject.Inject

class NetworkCheckerRepositoryImpl @Inject constructor(private val context: Context) :
    NetworkCheckerRepository {

    override fun isConnectionAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    override fun isAdbEnabled(context: Context): Boolean {
        return android.provider.Settings.Global.getInt(
            context.contentResolver,
            android.provider.Settings.Global.ADB_ENABLED, 0
        ) == 1
    }
}