package com.bi.gbass.tech.di

import android.content.Context
import com.bi.gbass.tech.data.DataStoreRepositoryImpl
import com.bi.gbass.tech.data.NetworkCheckerRepositoryImpl
import com.bi.gbass.tech.domain.DataStoreRepository
import com.bi.gbass.tech.domain.NetworkCheckerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetwork(@ApplicationContext context: Context): NetworkCheckerRepository {
        return NetworkCheckerRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideUserDataStorage(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }


}