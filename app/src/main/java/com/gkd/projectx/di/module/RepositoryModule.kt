package com.gkd.projectx.di.module

import com.gkd.data.managers.CharactersRepository
import com.gkd.data.managers.CharactersRepositoryImpl
import com.gkd.data.services.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCharactersRepository(characterApi: CharacterService): CharactersRepository {
        return CharactersRepositoryImpl(characterApi)
    }
}
