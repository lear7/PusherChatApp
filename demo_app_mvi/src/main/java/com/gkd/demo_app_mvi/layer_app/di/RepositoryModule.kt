package com.gkd.demo_app_mvi.layer_app.di

import com.gkd.demo_app_mvi.layer_data.managers.CharactersRepository
import com.gkd.demo_app_mvi.layer_data.managers.CharactersRepositoryImpl
import com.gkd.demo_app_mvi.layer_data.services.CharacterService
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
