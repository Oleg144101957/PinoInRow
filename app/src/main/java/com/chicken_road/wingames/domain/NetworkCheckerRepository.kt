package com.chicken_road.wingames.domain

interface NetworkCheckerRepository {
    fun isConnectionAvailable(): Boolean
}