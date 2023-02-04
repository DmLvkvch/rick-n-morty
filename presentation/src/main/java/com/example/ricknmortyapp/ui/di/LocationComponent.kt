package com.example.ricknmortyapp.ui.di

import androidx.lifecycle.ViewModel
import com.example.ricknmortyapp.di.annotation.ViewModelKey
import com.example.ricknmortyapp.ui.character.CharacterViewModel
import com.example.ricknmortyapp.ui.location.LocationFragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [CharacterFragmentModule::class])
interface LocationComponent {
    fun inject(value: LocationFragment)
}

@Module
interface LocationFragmentComponent {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterViewModel::class)
    fun bindMainViewModel(viewModel: CharacterViewModel): ViewModel
}