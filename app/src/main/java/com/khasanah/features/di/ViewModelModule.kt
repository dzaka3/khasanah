package com.khasanah.features.di

import com.khasanah.features.viewModel.KhasanahViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        KhasanahViewModel()
    }
}