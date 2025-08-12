package com.pl.nk.col.lec.domain

interface GaidRepository {

    suspend fun getGaid():String

}