package com.ze.us.ga.m.data

import android.content.Context
import androidx.core.content.edit
import com.ze.us.ga.m.domain.DataStoreRepository
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val context: Context) :
    DataStoreRepository {

    private val pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)


    override fun getUrl(): String {
        return pref.getString(GOAL, EMPTY) ?: EMPTY
    }

    override fun saveUrl(newGoalToSave: String) {
        pref.edit { putString(GOAL, newGoalToSave) }
    }

    override fun setSpeed(newSpeed: Float) {
        pref.edit { putFloat(SPEED, newSpeed) }
    }


    override fun getSpeed(): Float {
        return pref.getFloat(SPEED, 5f)
    }

    override fun saveAdb(adb: Boolean) {
        pref.edit { putBoolean(ADB, adb) }
    }

    override fun getAdb(): Boolean? {
        return if (pref.contains(ADB)) {
            pref.getBoolean(ADB, false)
        } else {
            null
        }
    }

    companion object {
        const val ADB = "ADB"
        private const val SHARED_PREF_NAME = "USER STORAGE BY SHARED PREFERENCES"
        private const val GOAL = "GOAL"
        private const val EMPTY = "EMPTY"
        private const val SPEED = "speed"
    }
}


