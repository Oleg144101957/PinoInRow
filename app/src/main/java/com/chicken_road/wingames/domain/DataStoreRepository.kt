package com.chicken_road.wingames.domain

interface DataStoreRepository {

    fun saveUrl(newGoalToSave: String)
    fun getUrl(): String
    fun setSpeed(newSpeed: Float)
    fun getSpeed(): Float
}



