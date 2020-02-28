package com.example.truecallerapp

import com.example.truecallerapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class AppApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(applicationContext)

    companion object {
        lateinit var INSTANCE: AppApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}