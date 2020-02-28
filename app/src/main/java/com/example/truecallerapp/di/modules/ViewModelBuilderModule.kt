package com.example.truecallerapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.truecallerapp.di.factories.ViewModelKey
import com.example.truecallerapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilderModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}