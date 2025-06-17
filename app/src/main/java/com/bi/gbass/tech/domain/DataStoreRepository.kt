package com.bi.gbass.tech.domain

interface DataStoreRepository {

    fun saveUrl(newGoalToSave: String)
    fun getUrl(): String
    fun setSpeed(newSpeed: Float)
    fun getSpeed(): Float
    fun saveAdb(adb: Boolean)
    fun getAdb(): Boolean?
}



