package com.ze.us.ga.m.domain

import android.content.Context

interface PushServiceInitializer {

    fun initializePushService(advertID: String, context: Context)

}