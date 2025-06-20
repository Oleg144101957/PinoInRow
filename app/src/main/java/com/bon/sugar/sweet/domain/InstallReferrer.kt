package com.bon.sugar.sweet.domain

interface InstallReferrer {

    suspend fun fetchReferrer(): String?
}
