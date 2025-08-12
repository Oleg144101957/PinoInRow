package com.pl.nk.col.lec.di

import android.content.Context
import com.pl.nk.col.lec.data.AppsRepositoryImpl
import com.pl.nk.col.lec.data.DataStoreRepositoryImpl
import com.pl.nk.col.lec.data.GaidRepositoryImpl
import com.pl.nk.col.lec.data.InstallReferrerImpl
import com.pl.nk.col.lec.data.NetworkCheckerRepositoryImpl
import com.pl.nk.col.lec.data.PostErrorRepositoryImpl
import com.pl.nk.col.lec.data.PushServiceInitializerImpl
import com.pl.nk.col.lec.domain.AppsRepository
import com.pl.nk.col.lec.domain.DataStoreRepository
import com.pl.nk.col.lec.domain.GaidRepository
import com.pl.nk.col.lec.domain.InstallReferrer
import com.pl.nk.col.lec.domain.NetworkCheckerRepository
import com.pl.nk.col.lec.domain.PostErrorRepository
import com.pl.nk.col.lec.domain.PushServiceInitializer
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