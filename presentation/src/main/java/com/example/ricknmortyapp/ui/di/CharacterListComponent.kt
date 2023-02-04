package com.example.ricknmortyapp.ui.di

import androidx.lifecycle.ViewModel
import com.example.ricknmortyapp.di.annotation.ViewModelKey
import com.example.ricknmortyapp.ui.character.CharacterListFragment
import com.example.ricknmortyapp.ui.character.CharacterListViewModel
import com.example.ricknmortyapp.ui.character.CharacterNavigationFragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap


@Subcomponent(modules = [CharacterListFragmentModule::class])
interface CharacterListComponent {
    fun inject(value: CharacterListFragment)

    fun inject(value: CharacterNavigationFragment)
}

@Module
interface CharacterListFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    fun bindMainViewModel(viewModel: CharacterListViewModel): ViewModel
}