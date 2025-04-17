package com.chicken_road.wingames.di

import android.content.Context
import com.chicken_road.wingames.data.DataStoreRepositoryImpl
import com.chicken_road.wingames.data.NetworkCheckerRepositoryImpl
import com.chicken_road.wingames.domain.DataStoreRepository
import com.chicken_road.wingames.domain.NetworkCheckerRepository
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