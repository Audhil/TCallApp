package com.example.truecallerapp.di.component

import android.content.Context
import com.example.truecallerapp.AppApplication
import com.example.truecallerapp.di.modules.APIModule
import com.example.truecallerapp.di.modules.ActivityBuilderModule
import com.example.truecallerapp.di.modules.OtherModule
import com.example.truecallerapp.di.modules.ViewModelBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        ViewModelBuilderModule::class,
        OtherModule::class,
        APIModule::class
    ]
)
interface AppComponent : AndroidInjector<AppApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
