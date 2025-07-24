package com.ze.us.ga.m.domain

interface InstallReferrer {

    suspend fun fetchReferrer(): String?
}
