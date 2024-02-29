package com.aryanto.github.di

import com.aryanto.github.ui.home.HomeVM
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeVM(get()) }
}