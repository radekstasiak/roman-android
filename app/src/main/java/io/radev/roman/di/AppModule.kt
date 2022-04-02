package io.radev.roman.di

import io.radev.roman.CoroutineDispatcher
import io.radev.shared.data.PlacesRepository
import io.radev.shared.data.PlacesRepositoryImpl
import io.radev.shared.domain.GetPlacesUseCase
import io.radev.shared.domain.GetPlacesUseCaseImpl
import org.koin.dsl.bind
import org.koin.dsl.module

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

val appModule = module {
    single { CoroutineDispatcher() }
    single { PlacesRepositoryImpl(get()) } bind PlacesRepository::class
    single { GetPlacesUseCaseImpl(get()) } bind GetPlacesUseCase::class
}

