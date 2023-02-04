package com.example.ricknmortyapp.ui.di

import androidx.lifecycle.ViewModel
import com.example.ricknmortyapp.di.annotation.ViewModelKey
import com.example.ricknmortyapp.ui.episode.EpisodeListFragment
import com.example.ricknmortyapp.ui.episode.EpisodeListViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [EpisodeListFragmentModule::class])
interface EpisodeListComponent {
    fun inject(value: EpisodeListFragment)
}

@Module
interface EpisodeListFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeListViewModel::class)
    fun bindMainViewModel(viewModel: EpisodeListViewModel): ViewModel
}