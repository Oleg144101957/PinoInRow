package com.bon.sugar.sweet.domain

interface GaidRepository {

    suspend fun getGaid():String

}