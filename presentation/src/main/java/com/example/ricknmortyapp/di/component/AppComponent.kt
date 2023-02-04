package com.example.ricknmortyapp.di.component

import android.content.Context
import com.example.ricknmortyapp.di.modules.AppModule
import com.example.ricknmortyapp.di.modules.RestModule
import com.example.ricknmortyapp.di.modules.ViewModelModule
import com.example.ricknmortyapp.ui.di.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RestModule::class, ViewModelModule::class])
interface AppComponent {

    val context: Context

    val characterListComponent: CharacterListComponent

    val locationListComponent: LocationListComponent

    val characterComponent: CharacterComponent

    val locationComponent: LocationComponent

    val episodeComponent: EpisodeComponent

    val episodeListComponent: EpisodeListComponent
}