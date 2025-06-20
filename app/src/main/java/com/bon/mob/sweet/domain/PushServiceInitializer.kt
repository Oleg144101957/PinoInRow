package com.bon.mob.sweet.domain

import android.content.Context

interface PushServiceInitializer {

    fun initializePushService(advertID: String, context: Context)

}