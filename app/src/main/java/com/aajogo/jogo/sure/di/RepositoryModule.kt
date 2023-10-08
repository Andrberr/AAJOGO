package com.aajogo.jogo.sure.di

import com.aajogo.jogo.sure.data.AajogoRepositoryImpl
import com.aajogo.jogo.sure.domain.AajogoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun getRepository(impl: AajogoRepositoryImpl): AajogoRepository
}