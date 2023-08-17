package com.pascal.medis_ku.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.pascal.medis_ku.data.datasource.NetworkDatasource
import com.pascal.medis_ku.data.datasource.NetworkDatasourceImpl
import com.pascal.medis_ku.data.network.service.NetworkService
import com.pascal.medis_ku.data.repository.NetworkRepository
import com.pascal.medis_ku.data.repository.NetworkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object MainModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkModule {
        @Provides
        @Singleton
        fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
            return ChuckerInterceptor.Builder(context).build()
        }

        @Provides
        @Singleton
        fun provideNetworkService(chuckerInterceptor: ChuckerInterceptor): NetworkService {
            return NetworkService.invoke(chuckerInterceptor)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object DataSourceModule {
        @Provides
        @Singleton
        fun provideInfoDatasource(networkService: NetworkService): NetworkDatasource {
            return NetworkDatasourceImpl(networkService)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {
        @Provides
        @Singleton
        fun provideNetworkRepository(networkDatasource: NetworkDatasource): NetworkRepository {
            return NetworkRepositoryImpl(networkDatasource)
        }
    }
}