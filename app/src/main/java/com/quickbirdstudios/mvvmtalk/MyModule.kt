package com.quickbirdstudios.mvvmtalk

import android.arch.lifecycle.ViewModel
import com.quickbirdstudios.quickboot.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


/**
 * Created by Malte Bucksch on 10/04/2018.
 */
@Singleton
@Module
abstract class MyModule {
    @Binds
    @IntoMap
    @ViewModelKey(TranslatorViewModel::class)
    abstract fun bindsTranslatorViewModel(translatorViewModelImpl: TranslatorViewModelImpl): ViewModel
}

