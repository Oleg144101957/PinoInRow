package com.bon.sugar.sweet.data

import android.content.Context
import com.bon.sugar.sweet.domain.PushServiceInitializer
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
        private const val ONE_SIGNAL_DEV_KEY = "22503bba-c421-437b-8c2d-3da580086387"
    }
}