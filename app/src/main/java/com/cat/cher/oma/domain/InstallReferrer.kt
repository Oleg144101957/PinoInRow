package com.cat.cher.oma.domain

interface InstallReferrer {

    suspend fun fetchReferrer(): String?
}
