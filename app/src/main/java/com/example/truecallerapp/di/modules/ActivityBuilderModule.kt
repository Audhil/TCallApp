package com.example.truecallerapp.di.modules

import com.example.truecallerapp.di.factories.ViewModelBuilder
import com.example.truecallerapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    abstract fun bindBaseActivity(): MainActivity
}