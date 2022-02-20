package io.radev.roman.di

import io.radev.roman.CoroutineDispatcher
import io.radev.roman.data.PlacesRepository
import io.radev.roman.data.PlacesRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

val appModule = module {
    single { CoroutineDispatcher() }
    single { PlacesRepositoryImpl(get(), get()) } bind PlacesRepository::class
}

