package com.boo.me.ran.g.domain

interface InstallReferrer {

    suspend fun fetchReferrer(): String?
}
