package com.bon.mob.sweet.domain

interface GaidRepository {

    suspend fun getGaid():String

}