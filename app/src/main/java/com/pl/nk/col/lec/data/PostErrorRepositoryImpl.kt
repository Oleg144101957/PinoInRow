package com.pl.nk.col.lec.data

import com.google.firebase.database.FirebaseDatabase
import com.pl.nk.col.lec.domain.PostErrorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tr.tp.tech.inves.data.ErrorMessage
import javax.inject.Inject

class PostErrorRepositoryImpl @Inject constructor() : PostErrorRepository {

    private val database = FirebaseDatabase.getInstance().reference

    override suspend fun postError(message: ErrorMessage) {
        withContext(Dispatchers.IO) {
            val uniqueKey = "error_${System.currentTimeMillis()}"
            database.child(uniqueKey).setValue(message.message)
        }
    }
}


