package com.example.ricknmortyapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data.database.DatabaseStorage
import com.example.data.repository.CharacterRepository
import com.example.data.repository.EpisodeRepository
import com.example.data.repository.LocationRepository
import com.example.domain.interactors.*
import com.example.domain.repository.ICharacterRepository
import com.example.domain.repository.IEpisodeRepository
import com.example.domain.repository.ILocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.InnerAppModule::class])
class AppModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideDatabaseStorage() = Room.databaseBuilder(
        context, DatabaseStorage::class.java, DatabaseStorage.DATA_BASE_STORAGE
    ).build()

    @Module
    interface InnerAppModule {

        @Binds
        @Singleton
        fun provideCharacterRepository(repository: CharacterRepository): ICharacterRepository

        @Binds
        @Singleton
        fun provideLocationRepository(repository: LocationRepository): ILocationRepository

        @Binds
        @Singleton
        fun provideEpisodeRepository(repository: EpisodeRepository): IEpisodeRepository

        @Singleton
        @Binds
        fun provideCharacterInteractor(characterInteractor: CharacterInteractor): ICharacterInteractor

        @Singleton
        @Binds
        fun provideLocationInteractor(locationInteractor: LocationInteractor): ILocationInteractor

        @Singleton
        @Binds
        fun provideEpisodeInteractor(episodeInteractor: EpisodeInteractor): IEpisodeInteractor
    }
}