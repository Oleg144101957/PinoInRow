package com.pl.nk.col.lec.domain

interface DataStoreRepository {

    fun saveUrl(newGoalToSave: String)
    fun getUrl(): String
    fun setSpeed(newSpeed: Float)
    fun getSpeed(): Float
    fun saveAdb(adb: Boolean)
    fun getAdb(): Boolean?

    fun saveBalance(balance: Int)

    // 🔹 Получение баланса
    fun getBalance(): Int
}



