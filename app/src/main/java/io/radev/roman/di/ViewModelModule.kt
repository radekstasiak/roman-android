package io.radev.roman.di

import io.radev.roman.ui.places.PlacesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

val viewModelModule = module {
    viewModel { PlacesViewModel(get(), get()) }
}