package com.bon.mob.sweet.domain

interface InstallReferrer {

    suspend fun fetchReferrer(): String?
}
