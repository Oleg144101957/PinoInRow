package com.bon.sugar.sweet.di

import android.content.Context
import com.bon.sugar.sweet.data.AppsRepositoryImpl
import com.bon.sugar.sweet.data.DataStoreRepositoryImpl
import com.bon.sugar.sweet.data.GaidRepositoryImpl
import com.bon.sugar.sweet.data.InstallReferrerImpl
import com.bon.sugar.sweet.data.NetworkCheckerRepositoryImpl
import com.bon.sugar.sweet.data.PushServiceInitializerImpl
import com.bon.sugar.sweet.domain.AppsRepository
import com.bon.sugar.sweet.domain.DataStoreRepository
import com.bon.sugar.sweet.domain.GaidRepository
import com.bon.sugar.sweet.domain.InstallReferrer
import com.bon.sugar.sweet.domain.NetworkCheckerRepository
import com.bon.sugar.sweet.domain.PushServiceInitializer
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

    @Provides
    @Singleton
    fun provideAppsRepository(): AppsRepository {
        return AppsRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePushRepository(): PushServiceInitializer {
        return PushServiceInitializerImpl()
    }

    @Provides
    @Singleton
    fun provideReferrer(@ApplicationContext context: Context): InstallReferrer {
        return InstallReferrerImpl(context)
    }

    @Provides
    @Singleton
    fun provideGaidRepository(@ApplicationContext context: Context): GaidRepository {
        return GaidRepositoryImpl(context)
    }


}