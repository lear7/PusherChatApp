package com.gkd.projectx.di.module

import com.gkd.data.managers.CharactersRepository
import com.gkd.data.managers.CharactersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @BindCharactersManager
    @Binds
    abstract fun bindCharactersManager(charactersRepository: CharactersRepositoryImpl): CharactersRepository

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindCharactersManager
