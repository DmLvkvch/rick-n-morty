package com.example.ricknmortyapp.app

import android.app.Application
import com.example.ricknmortyapp.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.initAppComponent(this)
    }
}