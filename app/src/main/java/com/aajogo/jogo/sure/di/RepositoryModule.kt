package com.aajogo.jogo.sure.di

import com.aajogo.jogo.sure.data.RepositoryImpl
import com.aajogo.jogo.sure.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun getRepository(impl: RepositoryImpl): Repository
}