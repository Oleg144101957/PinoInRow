package com.ze.us.ga.m.domain

import tr.tp.tech.inves.data.ErrorMessage


interface PostErrorRepository {

    suspend fun postError(message: ErrorMessage)

}