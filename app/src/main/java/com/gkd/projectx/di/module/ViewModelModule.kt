package com.gkd.projectx.di.module

import androidx.lifecycle.ViewModel
import com.gkd.projectx.common.BaseViewModel
import com.gkd.projectx.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {

    @BindMainViewModel
    @Binds
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindMainViewModel
