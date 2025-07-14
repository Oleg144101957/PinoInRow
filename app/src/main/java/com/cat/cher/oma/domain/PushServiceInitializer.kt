package com.cat.cher.oma.domain

import android.content.Context

interface PushServiceInitializer {

    fun initializePushService(advertID: String, context: Context)

}