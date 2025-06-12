package com.bi.gbass.tech.domain

interface NetworkCheckerRepository {
    fun isConnectionAvailable(): Boolean
}