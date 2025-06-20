package com.bon.mob.sweet.domain

interface DataStoreRepository {

    fun saveUrl(newGoalToSave: String)
    fun getUrl(): String
    fun setSpeed(newSpeed: Float)
    fun getSpeed(): Float
    fun saveAdb(adb: Boolean)
    fun getAdb(): Boolean?
}



