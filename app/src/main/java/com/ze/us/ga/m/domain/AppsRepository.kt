package com.ze.us.ga.m.domain

import android.content.Context

interface AppsRepository {

    suspend fun provideFlyer(contextActivity: Context, appsDevKey: String): MutableMap<String, Any?>

    suspend fun provideDIUU(contextActivity: Context): String
}