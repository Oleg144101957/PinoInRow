package com.pl.nk.col.lec.domain

import tr.tp.tech.inves.data.ErrorMessage


interface PostErrorRepository {

    suspend fun postError(message: ErrorMessage)

}