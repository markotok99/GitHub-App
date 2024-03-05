package com.aryanto.github.di

import com.aryanto.github.ui.activitys.detail.DetailVM
import com.aryanto.github.ui.activitys.home.HomeVM
import com.aryanto.github.ui.fragments.followers.FollowersVM
import com.aryanto.github.ui.fragments.following.FollowingVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeVM(get()) }
    single { DetailVM(get()) }
    single { FollowersVM(get()) }
    single { FollowingVM(get()) }
}