package com.ze.us.ga.m.domain

import android.content.Context

interface NetworkCheckerRepository {
    fun isConnectionAvailable(): Boolean
    fun isAdbEnabled(context: Context): Boolean
}