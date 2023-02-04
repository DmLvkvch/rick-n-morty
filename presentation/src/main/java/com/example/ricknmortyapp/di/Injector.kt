package com.example.ricknmortyapp.di

import android.content.Context
import com.example.ricknmortyapp.di.component.AppComponent
import com.example.ricknmortyapp.di.component.DaggerAppComponent
import com.example.ricknmortyapp.di.modules.AppModule
import com.example.ricknmortyapp.di.modules.RestModule
import com.example.ricknmortyapp.ui.di.*

object Injector {

    lateinit var appComponent: AppComponent

    val characterListComponent: CharacterListComponent
        get() {
            return appComponent.characterListComponent
        }

    val locationListComponent: LocationListComponent
        get() {
            return appComponent.locationListComponent
        }

    val characterComponent: CharacterComponent
        get() {
            return appComponent.characterComponent
        }

    val locationComponent: LocationComponent
        get() {
            return appComponent.locationComponent
        }

    val episodeComponent: EpisodeComponent
        get() {
            return appComponent.episodeComponent
        }

    val episodeListComponent: EpisodeListComponent
        get() {
            return appComponent.episodeListComponent
        }

    internal fun initAppComponent(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .restModule(RestModule())
            .build()
    }

}