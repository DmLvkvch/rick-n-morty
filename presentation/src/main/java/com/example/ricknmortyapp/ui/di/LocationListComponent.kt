package com.example.ricknmortyapp.ui.di

import androidx.lifecycle.ViewModel
import com.example.ricknmortyapp.di.annotation.ViewModelKey
import com.example.ricknmortyapp.ui.location.LocationListFragment
import com.example.ricknmortyapp.ui.location.LocationListViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap


@Subcomponent(modules = [LocationListFragmentModule::class])
interface LocationListComponent {
    fun inject(value: LocationListFragment)
}

@Module
interface LocationListFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(LocationListViewModel::class)
    fun bindMainViewModel(viewModel: LocationListViewModel): ViewModel
}