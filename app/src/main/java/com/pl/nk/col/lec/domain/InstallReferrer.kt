package com.pl.nk.col.lec.domain

interface InstallReferrer {

    suspend fun fetchReferrer(): String?
}
