package com.quickbirdstudios.mvvmtalk

import com.quickbirdstudios.quickboot.di.QuickComponent
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Malte Bucksch on 10/04/2018.
 */
@Singleton
@Component(modules = [MyModule::class])
interface MyComponent: QuickComponent {

}