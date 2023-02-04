package com.example.ricknmortyapp.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.ricknmortyapp.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}