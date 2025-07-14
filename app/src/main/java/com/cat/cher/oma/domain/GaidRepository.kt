package com.cat.cher.oma.domain

interface GaidRepository {

    suspend fun getGaid():String

}