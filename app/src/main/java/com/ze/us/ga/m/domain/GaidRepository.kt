package com.ze.us.ga.m.domain

interface GaidRepository {

    suspend fun getGaid():String

}