package com.example.ricknmortyapp.ui.di

import androidx.lifecycle.ViewModel
import com.example.ricknmortyapp.di.annotation.ViewModelKey
import com.example.ricknmortyapp.ui.episode.EpisodeFragment
import com.example.ricknmortyapp.ui.episode.EpisodeViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [EpisodeFragmentModule::class])
interface EpisodeComponent {
    fun inject(value: EpisodeFragment)
}

@Module
interface EpisodeFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeViewModel::class)
    fun bindMainViewModel(viewModel: EpisodeViewModel): ViewModel
}