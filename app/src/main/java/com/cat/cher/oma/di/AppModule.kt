package com.cat.cher.oma.di

import android.content.Context
import com.cat.cher.oma.data.AppsRepositoryImpl
import com.cat.cher.oma.data.DataStoreRepositoryImpl
import com.cat.cher.oma.data.GaidRepositoryImpl
import com.cat.cher.oma.data.InstallReferrerImpl
import com.cat.cher.oma.data.NetworkCheckerRepositoryImpl
import com.cat.cher.oma.data.PushServiceInitializerImpl
import com.cat.cher.oma.domain.AppsRepository
import com.cat.cher.oma.domain.DataStoreRepository
import com.cat.cher.oma.domain.GaidRepository
import com.cat.cher.oma.domain.InstallReferrer
import com.cat.cher.oma.domain.NetworkCheckerRepository
import com.cat.cher.oma.domain.PushServiceInitializer
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