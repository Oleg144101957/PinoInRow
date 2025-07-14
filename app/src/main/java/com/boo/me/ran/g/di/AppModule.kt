package com.boo.me.ran.g.di

import android.content.Context
import com.boo.me.ran.g.data.AppsRepositoryImpl
import com.boo.me.ran.g.data.DataStoreRepositoryImpl
import com.boo.me.ran.g.data.GaidRepositoryImpl
import com.boo.me.ran.g.data.InstallReferrerImpl
import com.boo.me.ran.g.data.NetworkCheckerRepositoryImpl
import com.boo.me.ran.g.data.PushServiceInitializerImpl
import com.boo.me.ran.g.domain.AppsRepository
import com.boo.me.ran.g.domain.DataStoreRepository
import com.boo.me.ran.g.domain.GaidRepository
import com.boo.me.ran.g.domain.InstallReferrer
import com.boo.me.ran.g.domain.NetworkCheckerRepository
import com.boo.me.ran.g.domain.PushServiceInitializer
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