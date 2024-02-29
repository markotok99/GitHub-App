package com.aryanto.github.utils

import android.app.Application
import com.aryanto.github.di.appModule
import com.aryanto.github.di.repositoryModule
import com.aryanto.github.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule, repositoryModule, viewModelModule)
        }

    }

}