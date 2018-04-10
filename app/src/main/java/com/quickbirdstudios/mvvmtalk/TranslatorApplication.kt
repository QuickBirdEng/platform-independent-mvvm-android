package com.quickbirdstudios.mvvmtalk

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.quickbirdstudios.quickboot.Quick
import com.quickbirdstudios.quickboot.architecture.QuickApplication
import com.quickbirdstudios.quickboot.di.QuickComponent
import com.quickbirdstudios.quickboot.di.ViewModelKey
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


/**
 * Created by Malte Bucksch on 10/04/2018.
 */
class TranslatorApplication: Application(), QuickApplication<MyComponent>{
    override fun createComponent(): MyComponent {
        return DaggerMyComponent.create()
    }

    override fun onCreate() {
        super.onCreate()

        Quick.boot(this)
    }
}
