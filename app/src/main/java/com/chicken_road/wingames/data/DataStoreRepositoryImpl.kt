package com.chicken_road.wingames.data

import android.content.Context
import androidx.core.content.edit
import com.chicken_road.wingames.domain.DataStoreRepository
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

    companion object {
        private const val SHARED_PREF_NAME = "USER STORAGE BY SHARED PREFERENCES"
        private const val GOAL = "GOAL"
        private const val EMPTY = "EMPTY"
    }
}


