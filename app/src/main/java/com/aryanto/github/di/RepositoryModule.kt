package com.aryanto.github.di

import com.aryanto.github.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get()) }
}