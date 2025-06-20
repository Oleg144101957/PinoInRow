package com.bon.mob.sweet.data

import android.content.Context
import com.bon.mob.sweet.domain.PushServiceInitializer
import com.onesignal.OneSignal
import javax.inject.Inject

class PushServiceInitializerImpl @Inject constructor() :
    PushServiceInitializer {

    override fun initializePushService(advertID: String, context: Context) {
        OneSignal.initWithContext(context)
        OneSignal.setAppId(ONE_SIGNAL_DEV_KEY)
        OneSignal.setExternalUserId(advertID)
        OneSignal.promptForPushNotifications(true)
    }

    companion object {
        private const val ONE_SIGNAL_DEV_KEY = "3ee55bbc-e54e-4a92-8c94-22993a294d7e"
    }
}