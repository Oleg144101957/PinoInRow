package com.ze.us.ga.m.di

import android.content.Context
import com.ze.us.ga.m.data.AppsRepositoryImpl
import com.ze.us.ga.m.data.DataStoreRepositoryImpl
import com.ze.us.ga.m.data.GaidRepositoryImpl
import com.ze.us.ga.m.data.InstallReferrerImpl
import com.ze.us.ga.m.data.NetworkCheckerRepositoryImpl
import com.ze.us.ga.m.data.PostErrorRepositoryImpl
import com.ze.us.ga.m.data.PushServiceInitializerImpl
import com.ze.us.ga.m.domain.AppsRepository
import com.ze.us.ga.m.domain.DataStoreRepository
import com.ze.us.ga.m.domain.GaidRepository
import com.ze.us.ga.m.domain.InstallReferrer
import com.ze.us.ga.m.domain.NetworkCheckerRepository
import com.ze.us.ga.m.domain.PostErrorRepository
import com.ze.us.ga.m.domain.PushServiceInitializer
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

    @Provides
    @Singleton
    fun providePostError(): PostErrorRepository {
        return PostErrorRepositoryImpl()
    }


}