package com.example.ricknmortyapp.ui.di

import androidx.lifecycle.ViewModel
import com.example.ricknmortyapp.di.annotation.ViewModelKey
import com.example.ricknmortyapp.ui.character.CharacterFragment
import com.example.ricknmortyapp.ui.character.CharacterViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [CharacterFragmentModule::class])
interface CharacterComponent {
    fun inject(value: CharacterFragment)
}

@Module
interface CharacterFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterViewModel::class)
    fun bindMainViewModel(viewModel: CharacterViewModel): ViewModel
}