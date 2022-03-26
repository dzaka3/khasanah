package com.khasanah.features.di

import com.khasanah.features.data.repository.impl.RegistrationRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val BACKGROUND_DISPATCHER = "background_dispatcher"
private const val MAIN_DISPATCHER = "main_dispatcher"

val repoModule = module {
    single{
        //get() is constructor injection
        RegistrationRepositoryImpl(androidContext(),get(named(BACKGROUND_DISPATCHER)),get())
    }
    /**
     * Mapper, supaya bisa inject() -> field injection
     */

    //dispatche
    single(named(MAIN_DISPATCHER)) { Dispatchers.Main }
    single(named(BACKGROUND_DISPATCHER)) { Dispatchers.IO }
}
